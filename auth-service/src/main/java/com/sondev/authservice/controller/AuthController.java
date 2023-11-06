package com.sondev.authservice.controller;

import com.sondev.authservice.dto.response.LoginDto;
import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.Utils;
import com.sondev.authservice.dto.request.LoginRequest;
import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody @Validated LoginRequest loginRequest) {
        log.info("*** UserDto List, controller; login *");
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterRequest registerRequest) {
        log.info("*** UserDto List, controller; register *");
        return ResponseEntity.ok().body(authService.register(registerRequest));
    }

    @GetMapping("/hello")
    public String test() {
        log.info("*** UserDto List, controller; login *");
        return "Hello";
    }
}
