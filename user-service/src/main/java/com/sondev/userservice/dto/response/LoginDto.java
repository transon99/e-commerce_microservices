package com.sondev.userservice.dto.response;

import com.sondev.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private String accessToken;
    private String status;
    private String type;
    private String fullName;
    private Role role;
}
