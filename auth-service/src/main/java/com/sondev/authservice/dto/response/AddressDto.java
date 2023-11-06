package com.sondev.authservice.dto.response;

import com.sondev.authservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto extends AbstractDto<String> {
    private String id;

    private String fullAddress;

    private String district;

    private String city;

}
