package com.sondev.productservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReduceQtyData {
    private String productId;
    private Integer quantity;
}
