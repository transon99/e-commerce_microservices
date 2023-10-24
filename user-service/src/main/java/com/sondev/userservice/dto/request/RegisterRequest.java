package com.sondev.userservice.dto.request;

import com.sondev.userservice.entity.Address;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private AddressRequest addressRequest;

    @Size(max = 255)
    private String userName;

    @Size(max = 255)
    private String password;
}
