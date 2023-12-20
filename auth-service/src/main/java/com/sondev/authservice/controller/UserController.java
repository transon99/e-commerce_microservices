package com.sondev.authservice.controller;

import com.sondev.authservice.service.UserService;
import com.sondev.common.response.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    public ResponseEntity<ResponseMessage> getCurrentUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(new ResponseMessage("OK", "Get current user successful !!!", userService.getCurrentUser(token)) );
    }
}
