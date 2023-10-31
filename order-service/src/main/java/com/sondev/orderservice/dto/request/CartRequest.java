package com.sondev.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartRequest {

    private Double price;

    private Integer quantity;

    private String productId;

}
