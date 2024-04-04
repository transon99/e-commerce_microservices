package com.sondev.authservice.dto.request;

import com.sondev.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private String zaloId;

    private Role role;

}
