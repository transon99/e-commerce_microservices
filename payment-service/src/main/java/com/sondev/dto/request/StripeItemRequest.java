package com.sondev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeItemRequest {
    private String productName;
    private int quantity;
    private double price;
    private String productId;
    private String userId;

}
