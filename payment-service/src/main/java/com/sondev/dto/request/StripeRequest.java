package com.sondev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeRequest {
    private List<StripeItemRequest> stripeItemList;
    private String orderId;
}
