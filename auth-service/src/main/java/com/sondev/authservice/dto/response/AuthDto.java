package com.sondev.authservice.dto.response;

import com.sondev.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDto {
    private String accessToken;
    private String refreshToken;
    private String status;
    private String type;
    private String fullName;
    private Role role;
    private String userId;
}
