package com.sondev.userservice.dto.response;

import com.sondev.userservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto<String> {
    private String id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private String email;

    private String phone;

}