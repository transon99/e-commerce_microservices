package com.sondev.userservice.controller;

import com.sondev.common.utils.Utils;
import com.sondev.userservice.dto.request.LoginRequest;
import com.sondev.userservice.dto.request.RegisterRequest;
import com.sondev.userservice.service.AuthService;
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
    public ResponseEntity login(@RequestBody @Validated LoginRequest loginRequest) {
        log.info("*** UserDto List, controller; login *");
        return Utils.checkStatusCodeAndResponse(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterRequest registerRequest) {
        log.info("*** UserDto List, controller; login *");
        return Utils.checkStatusCodeAndResponse(authService.register(registerRequest));
    }

    @GetMapping("/hello")
    public String test() {
        log.info("*** UserDto List, controller; login *");
        return "Hello";
    }
}
