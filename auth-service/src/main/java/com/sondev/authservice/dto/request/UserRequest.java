package com.sondev.authservice.dto.request;

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

    private String phone;

    private String zaloId;

}
