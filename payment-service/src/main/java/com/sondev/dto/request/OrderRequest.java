package com.sondev.dto.request;

import com.sondev.dto.OrderItemDto;
import com.sondev.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequest {
    @NotNull
    private String deliveryAddress;

    private String  paymentId;

    private double totalPrice;

    private Status status;

    private String userId;

    private List<OrderItemDto> orderItemRequest;

}