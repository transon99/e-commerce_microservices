package com.sondev.authservice.controller;

import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RefreshTokenRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.dto.request.SocialLoginRequest;
import com.sondev.authservice.service.AuthService;
import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody @Validated LoginRequest loginRequest) {
        log.info("*** UserDto List, controller; login *");
        return ResponseEntity.ok().body(new ResponseMessage(ResponseStatus.OK, "Login successful !!!", authService.login(loginRequest)) );
    }

    @PostMapping("/login/facebook")
    public ResponseEntity<ResponseMessage> loginFacebook(@RequestBody @Validated SocialLoginRequest loginFacebookRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(ResponseStatus.OK, "Login successful !!!", authService.loginFacebook(loginFacebookRequest)) );
    }

    @PostMapping("/login/zalo")
    public ResponseEntity<ResponseMessage> loginZalo(@RequestBody @Validated SocialLoginRequest loginZaloRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(ResponseStatus.OK, "Login successful !!!", authService.loginZalo(loginZaloRequest)) );
    }

    @PostMapping("/login/google")
    public ResponseEntity<ResponseMessage> loginGoogle(@RequestBody @Validated SocialLoginRequest loginGoogleRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(ResponseStatus.OK, "Login successful !!!", authService.loginGoogle(loginGoogleRequest)) );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Validated RegisterRequest registerRequest) {
        log.info("*** UserDto List, controller; register *");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(ResponseStatus.OK, "Register successful !!!",authService.register(registerRequest)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseMessage> refreshToken(@RequestBody @Validated RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(ResponseStatus.OK, "refresh token successful!!!",authService.refreshToken(refreshTokenRequest)));
    }

    @PostMapping("/active-user/{token}")
    public ResponseEntity<ResponseMessage> activeUser( @PathVariable String token) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(ResponseStatus.OK, "active user successful !!!",authService.activeUser(token)));
    }
}
