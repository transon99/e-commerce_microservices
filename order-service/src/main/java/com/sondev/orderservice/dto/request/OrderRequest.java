package com.sondev.orderservice.dto.request;

import com.sondev.orderservice.dto.response.OrderItemDto;
import com.sondev.orderservice.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {

    @NotNull
    private String deliveryAddress;

    private String  paymentId;

    private double totalPrice;

//    private Status status;

    private String userId;

    private List<OrderItemDto> orderItemRequest;

}