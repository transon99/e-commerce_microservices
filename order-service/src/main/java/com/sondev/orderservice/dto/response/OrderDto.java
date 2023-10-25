package com.sondev.orderservice.dto.response;

import com.sondev.orderservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractDto<String> {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer status;
    private List<String> orderDetailIds;
}