package com.sondev.orderservice.dto.request;


import com.sondev.orderservice.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private PaymentMethod paymentMethod;
    private double totalPrice;

}