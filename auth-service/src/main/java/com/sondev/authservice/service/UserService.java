package com.sondev.authservice.service;

import com.sondev.common.response.ResponseDTO;

import java.util.Map;

public interface UserService {
    ResponseDTO getAllUser();

    ResponseDTO getUserById(String id);

    ResponseDTO updateUser(Map<String, Object> fields, String id);

    ResponseDTO deleteUser(String id);
}
