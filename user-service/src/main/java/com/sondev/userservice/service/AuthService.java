package com.sondev.userservice.service;

import com.sondev.common.response.ResponseDTO;
import com.sondev.userservice.dto.request.LoginRequest;
import com.sondev.userservice.dto.request.RegisterRequest;

public interface AuthService {

    ResponseDTO login(LoginRequest categoryRequest);

    ResponseDTO register(RegisterRequest registerRequest);

}
