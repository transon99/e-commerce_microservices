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

    private String userName;

    private String password;

    private String role;
}
