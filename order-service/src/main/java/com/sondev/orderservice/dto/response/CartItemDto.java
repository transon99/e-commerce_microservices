package com.sondev.orderservice.dto.response;

import com.sondev.orderservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto extends AbstractDto<String> {
    private String id;

    private Integer quantity;

    private String productId;

    private String userId;

    private String orderId;
}
