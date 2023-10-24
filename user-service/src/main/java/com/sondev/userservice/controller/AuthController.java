package com.sondev.userservice.controller;

import com.sondev.common.utils.Utils;
import com.sondev.userservice.dto.request.LoginRequest;
import com.sondev.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Validated LoginRequest loginRequest) {
        log.info("*** UserDto List, controller; login *");
        return Utils.checkStatusCodeAndResponse(authService.login(loginRequest));
    }
}
