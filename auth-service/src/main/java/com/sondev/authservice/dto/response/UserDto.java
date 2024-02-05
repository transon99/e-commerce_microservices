package com.sondev.authservice.dto.response;

import com.sondev.authservice.dto.AbstractDto;
import com.sondev.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends AbstractDto<String> {
    private String id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private String email;

    private String phone;

    private Role role;

    private AddressDto address;

}