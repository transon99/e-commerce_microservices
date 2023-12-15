package com.sondev.authservice.service;

import com.sondev.authservice.dto.request.UserRequest;
import com.sondev.authservice.dto.response.UserDto;
import com.sondev.authservice.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(UserRequest userRequest);

    List<UserDto> getAllUser();

    UserDto getUserById(String id);

    UserDto updateUser(Map<String, Object> fields, String id);

    String deleteUser(String id);
}
