package com.sondev.userservice.service.impl;

import com.sondev.common.exceptions.APIException;
import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.Utils;
import com.sondev.userservice.dto.request.LoginRequest;
import com.sondev.userservice.dto.request.RegisterRequest;
import com.sondev.userservice.entity.Address;
import com.sondev.userservice.entity.User;
import com.sondev.userservice.mapper.AddressMapper;
import com.sondev.userservice.mapper.UserMapper;
import com.sondev.userservice.repository.AddressRepository;
import com.sondev.userservice.repository.UserRepository;
import com.sondev.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public ResponseDTO login(LoginRequest categoryRequest) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDTO register(RegisterRequest registerRequest) {
        User userSave;
        Optional<User> currentUser = userRepository.findByUserNameOrEmail(registerRequest.getUserName(),
                registerRequest.getEmail());
        if (currentUser.isPresent()) {
            Address address = addressRepository.save(addressMapper.reqToEntity(registerRequest.getAddressRequest()));
            User user = userMapper.reqToEntity(registerRequest);
            user.setAddresses(Set.of(address));
            userSave = userRepository.save(user);
        } else {
            log.error("*** String, service; register user already exists *");
            throw new APIException("user already exists");
        }

        return Utils.getResponseSuccess(userSave.getId(), "Successfully!!!");
    }

}
