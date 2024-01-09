package com.sondev.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private String email;

    private String phone;

    private String role;

    private Set<AddressDto> addresses;

}