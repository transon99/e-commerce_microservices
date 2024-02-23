package com.sondev.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private String id;

    private Integer quantity;

    private String productId;

    private String userId;

}
