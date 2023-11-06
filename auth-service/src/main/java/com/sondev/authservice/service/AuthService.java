package com.sondev.authservice.service;

import com.sondev.authservice.dto.response.LoginDto;
import com.sondev.common.response.ResponseDTO;
import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RegisterRequest;

public interface AuthService {

    LoginDto login(LoginRequest categoryRequest);

    String register(RegisterRequest registerRequest);

}
