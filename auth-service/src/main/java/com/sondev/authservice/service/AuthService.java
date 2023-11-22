package com.sondev.authservice.service;

import com.sondev.authservice.dto.response.AuthDto;
import com.sondev.common.response.ResponseDTO;
import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    AuthDto login(LoginRequest categoryRequest);

    String register(RegisterRequest registerRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
