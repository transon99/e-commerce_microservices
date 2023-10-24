package com.sondev.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressRequest {
    private String fullAddress;
    private String district;
    private String city;
}
