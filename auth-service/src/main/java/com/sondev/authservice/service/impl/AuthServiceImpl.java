package com.sondev.authservice.service.impl;

import com.sondev.authservice.dto.request.*;
import com.sondev.authservice.event.MailEvent;
import com.sondev.authservice.dto.UserZalo;
import com.sondev.authservice.dto.response.AuthDto;
import com.sondev.authservice.entity.Address;
import com.sondev.authservice.entity.Role;
import com.sondev.authservice.entity.User;
import com.sondev.authservice.entity.VerificationToken;
import com.sondev.authservice.event.UserInfo;
import com.sondev.authservice.exceptions.UserAlreadyExistException;
import com.sondev.authservice.exceptions.UserNotActivatedException;
import com.sondev.authservice.feignclient.ZaloApi;
import com.sondev.authservice.mapper.AddressMapper;
import com.sondev.authservice.mapper.UserMapper;
import com.sondev.authservice.repository.AddressRepository;
import com.sondev.authservice.repository.UserRepository;
import com.sondev.authservice.repository.VerificationTokenRepository;
import com.sondev.authservice.security.jwt.JwtService;
import com.sondev.authservice.service.AuthService;
import com.sondev.authservice.service.UserService;
import com.sondev.authservice.service.VerificationService;
import com.sondev.authservice.utils.TempEmailUtils;
import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${secretPsw}")
    String secretPsw;

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final ZaloApi zaloApi;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, MailEvent> kafkaTemplate;

    @Override
    public AuthDto login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User userDetail = (User) authentication.getPrincipal();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", userDetail.getRole());
        extraClaims.put("userId", userDetail.getId());

        String accessToken = jwtService.generateAccessToken(userDetail, extraClaims);
        String refreshToken = jwtService.generateRefreshToken(userDetail);

        if (!userDetail.getEnabled()) {
            throw new APIException("User is not enable!!");
        }
        return AuthDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(String.valueOf(ResponseStatus.OK))
                .fullName(userDetail.getFirstName() + " " + userDetail.getLastName())
                .type("Bearer")
                .userId(userDetail.getId())
                .role(userDetail.getRole())
                .build();

    }

    @Override
    @Transactional
    public String register(RegisterRequest registerRequest) {
        User savedUser;
        Address address;
        Optional<User> currentUser = userRepository.findByEmail(registerRequest.getEmail());
        if (currentUser.isPresent()) {
            User existedUser = currentUser.get();
            if (!existedUser.isEnabled()) {
                throw new UserNotActivatedException(
                        "User with email " + registerRequest
                                .getEmail() + " has been registered but not activated. Please check your email.");
            } else {
                log.error("*** String, service; register user already exists *");
                throw new UserAlreadyExistException("user already exists");
            }


        }
        User user = userMapper.reqToEntity(registerRequest);

        user.setRole(Role.USER);
        user.setEnabled(false);
        user.setLocked(false);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        savedUser = userRepository.save(user);

        //Save the verification token for the user
        VerificationToken verificationToken = verificationService.saveUserVerificationToken(savedUser);

        //Publish event to Mail service
        kafkaTemplate.send("verification-mail", new MailEvent(UserInfo.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .build(), verificationToken.getToken()));

        return savedUser.getId();
    }

    @Override
    public AuthDto refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String token = refreshTokenRequest.getRefreshToken();
        if (token != null && jwtService.validateToken(token)) {
            String email = jwtService.getEmailFromToken(token);
            if (email != null) {
                User user = userRepository.findByEmail(email).orElseThrow();
                if (jwtService.validateToken(token)) {
                    Map<String, Object> extraClaims = new HashMap<>();
                    extraClaims.put("roles", user.getRole());
                    extraClaims.put("userId", user.getId());
                    String accessToken = jwtService.generateAccessToken(user, extraClaims);
                    return AuthDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(token)
                            .status(String.valueOf(ResponseStatus.OK))
                            .fullName(user.getFirstName() + " " + user.getLastName())
                            .type("Bearer")
                            .role(user.getRole())
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public AuthDto loginFacebook(SocialLoginRequest loginFacebookRequest) {
        Facebook facebook = new FacebookTemplate(loginFacebookRequest.getSocialAccessToken());
        final String[] fields = {"email", "picture"};

        org.springframework.social.facebook.api.User user = facebook.fetchObject("me",
                org.springframework.social.facebook.api.User.class, fields);
        User appUser;
        if (userRepository.existsByEmail(user.getEmail())) {
            appUser = userRepository.findByEmail(user.getEmail()).get();
        } else {
            appUser = userService.createUser(UserRequest.builder()
                    .email(user.getEmail())
                    .build());
        }

        return loginSocial(appUser);
    }

    @Override
    public AuthDto loginZalo(SocialLoginRequest loginZaloRequest) {
        UserZalo userZalo = zaloApi.getInformation(loginZaloRequest.getSocialAccessToken());
        User appUser;
        if (Boolean.TRUE.equals(userRepository.existsByZaloId(userZalo.getId()))) {
            appUser = userRepository.findByZaloId(userZalo.getId()).get();
        } else {
            appUser = userService.createUser(UserRequest.builder()
                    .email(TempEmailUtils.createTempEmail())
                    .zaloId(userZalo.getId())
                    .build());
        }

        return loginSocial(appUser);
    }

    @Override
    public AuthDto loginGoogle(SocialLoginRequest loginGoogleRequest) {
        return null;
    }

    @Override
    public String activeUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("Can't find Token " + token));
        User currentUser = userRepository.findById(verificationToken.getUserId()).orElseThrow(() -> new NotFoundException("Can't find user with id " + verificationToken.getUserId()));
        currentUser.setEnabled(true);

        return userRepository.save(currentUser).getId();
    }

    private AuthDto loginSocial(User user) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        secretPsw
                )
        );

        User userDetail = (User) authentication.getPrincipal();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", userDetail.getRole());
        extraClaims.put("userId", userDetail.getId());

        String accessToken = jwtService.generateAccessToken(userDetail, extraClaims);
        String refreshToken = jwtService.generateRefreshToken(userDetail);

        return AuthDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(String.valueOf(ResponseStatus.OK))
                .fullName(userDetail.getFirstName() + " " + userDetail.getLastName())
                .type("Bearer")
                .role(userDetail.getRole())
                .userId(userDetail.getId())
                .build();

    }

}
