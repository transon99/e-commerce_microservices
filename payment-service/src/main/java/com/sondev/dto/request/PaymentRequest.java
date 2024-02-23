package com.sondev.dto.request;

import com.sondev.entity.PaymentMethod;
import com.sondev.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private PaymentMethod paymentMethod;
    private double totalPrice;
    private StripeRequest stripeRequest;
}