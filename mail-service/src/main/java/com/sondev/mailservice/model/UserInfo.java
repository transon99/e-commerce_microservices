package com.sondev.mailservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
