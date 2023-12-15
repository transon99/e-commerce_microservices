package com.sondev.authservice.service.impl;

import com.sondev.authservice.dto.request.UserRequest;
import com.sondev.authservice.dto.response.UserDto;
import com.sondev.authservice.entity.Role;
import com.sondev.authservice.entity.User;
import com.sondev.authservice.mapper.UserMapper;
import com.sondev.authservice.repository.UserRepository;
import com.sondev.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${secretPsw}")
    String secretPsw;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequest userRequest) {
        User user = userMapper.reqUserToEntity(userRequest);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(secretPsw));
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        return null;
    }

    @Override
    public UserDto getUserById(String id) {
        return null;
    }

    @Override
    public UserDto updateUser(Map<String, Object> fields, String id) {
        return null;
    }

    @Override
    public String deleteUser(String id) {
        return null;
    }

}
