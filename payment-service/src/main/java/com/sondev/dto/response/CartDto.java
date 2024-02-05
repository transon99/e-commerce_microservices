package com.sondev.dto.response;


import com.sondev.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto extends AbstractDto<String> {
    private String id;
    private Double totalPrice;
    private Integer quantity;
    private List<CartItemDto> cartItemDtoList;
}