package com.sondev.authservice.service;

import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RefreshTokenRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.dto.request.SocialLoginRequest;
import com.sondev.authservice.dto.response.AuthDto;

public interface AuthService {

    AuthDto login(LoginRequest categoryRequest);

    String register(RegisterRequest registerRequest);

    AuthDto refreshToken(RefreshTokenRequest refreshTokenRequest);

    AuthDto loginFacebook(SocialLoginRequest loginFacebookRequest);

    AuthDto loginZalo(SocialLoginRequest loginZaloRequest);

    AuthDto loginGoogle(SocialLoginRequest loginGoogleRequest);

    String activeUser(String token);
}
