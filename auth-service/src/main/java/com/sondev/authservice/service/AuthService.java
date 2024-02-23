package com.sondev.authservice.service;

import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.dto.request.SocialLoginRequest;
import com.sondev.authservice.dto.response.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    AuthDto login(LoginRequest categoryRequest);

    String register(RegisterRequest registerRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    AuthDto loginFacebook(SocialLoginRequest loginFacebookRequest);

    AuthDto loginZalo(SocialLoginRequest loginZaloRequest);

    AuthDto loginGoogle(SocialLoginRequest loginGoogleRequest);

    String activeUser(String token);
}
