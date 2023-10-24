package com.sondev.userservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {

    @Size(max = 255)
    private String userNameOrEmail;
    @Size(max = 255)
    private String password;
}