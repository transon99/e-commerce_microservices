package com.sondev.userservice.service.impl;

import com.sondev.common.exceptions.APIException;
import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.Utils;
import com.sondev.userservice.dto.request.LoginRequest;
import com.sondev.userservice.dto.request.RegisterRequest;
import com.sondev.userservice.dto.response.LoginDto;
import com.sondev.userservice.entity.Address;
import com.sondev.userservice.entity.User;
import com.sondev.userservice.mapper.AddressMapper;
import com.sondev.userservice.mapper.UserMapper;
import com.sondev.userservice.repository.AddressRepository;
import com.sondev.userservice.repository.UserRepository;
import com.sondev.userservice.security.jwt.JwtService;
import com.sondev.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    public ResponseDTO login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(authentication);
        User userDetail = (User) authentication.getPrincipal();

//        if (userDetail.getEnabled()) {
//            throw new APIException("User is not enable!!");
//        }
        return Utils.getResponseSuccess(LoginDto.builder()
                .accessToken(jwtToken)
                .status("OK")
                .fullName(userDetail.getFirstName() + " " + userDetail.getLastName())
                .type("Bearer")
                .role(userDetail.getRole())
                .build(), "Successfully!!!");

    }

    @Override
    @Transactional
    public ResponseDTO register(RegisterRequest registerRequest) {
        User userSave;
        Address address;
        Optional<User> currentUser = userRepository.findByUserName(registerRequest.getUserName());
        if (currentUser.isEmpty()) {
            User user = new User();
            user = userMapper.reqToEntity(registerRequest);
            if (registerRequest.getAddressRequest() != null) {
                address = addressRepository.save(addressMapper.reqToEntity(registerRequest.getAddressRequest()));
                user.setAddresses(Set.of(address));
            } else {
                user.setAddresses(new HashSet<>());
            }
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userSave = userRepository.save(user);
        } else {
            log.error("*** String, service; register user already exists *");
            throw new APIException("user already exists");
        }

        return Utils.getResponseSuccess(userSave.getId(), "Successfully!!!");
    }

}
