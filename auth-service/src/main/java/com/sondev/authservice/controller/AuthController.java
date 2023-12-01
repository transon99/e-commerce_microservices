package com.sondev.authservice.controller;

import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.service.AuthService;
import com.sondev.common.response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok().body(new ResponseMessage("200", "Login successful !!!", authService.login(loginRequest)) );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Validated RegisterRequest registerRequest) {
        log.info("*** UserDto List, controller; register *");
        return ResponseEntity.ok().body(new ResponseMessage("200", "Login successful !!!",authService.register(registerRequest)));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
