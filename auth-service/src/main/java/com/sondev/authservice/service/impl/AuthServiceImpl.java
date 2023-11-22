package com.sondev.authservice.service.impl;

import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.dto.response.LoginDto;
import com.sondev.authservice.entity.Address;
import com.sondev.authservice.entity.Role;
import com.sondev.authservice.entity.User;
import com.sondev.authservice.exceptions.UserAlreadyExistException;
import com.sondev.authservice.mapper.AddressMapper;
import com.sondev.authservice.mapper.UserMapper;
import com.sondev.authservice.repository.AddressRepository;
import com.sondev.authservice.repository.UserRepository;
import com.sondev.authservice.security.jwt.JwtService;
import com.sondev.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginDto login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        User userDetail = (User) authentication.getPrincipal();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", userDetail.getRole());
        extraClaims.put("userId", userDetail.getId());

        String accessToken = jwtService.generateAccessToken(userDetail, extraClaims );
        String refreshToken = jwtService.generateRefreshToken(userDetail);

//        if (userDetail.getEnabled()) {
//            throw new APIException("User is not enable!!");
//        }
        return LoginDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status("OK")
                .fullName(userDetail.getFirstName() + " " + userDetail.getLastName())
                .type("Bearer")
                .role(userDetail.getRole())
                .build();

    }

    @Override
    @Transactional
    public String register(RegisterRequest registerRequest) {
        User userSave;
        Address address;
        Optional<User> currentUser = userRepository.findByUserName(registerRequest.getUserName());
        if (currentUser.isEmpty()) {
            User user = userMapper.reqToEntity(registerRequest);
            if (registerRequest.getAddressRequest() != null) {
                address = addressRepository.save(addressMapper.reqToEntity(registerRequest.getAddressRequest()));
                user.setAddresses(Set.of(address));
            } else {
                user.setAddresses(new HashSet<>());
            }
            if(StringUtils.isEmpty(registerRequest.getRole())){
                user.setRole(Role.USER);
            }
            user.setEnabled(true);
            user.setLocked(true);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userSave = userRepository.save(user);
        } else {
            log.error("*** String, service; register user already exists *");
            throw new UserAlreadyExistException("user already exists");
        }

        return userSave.getId();
    }

}
