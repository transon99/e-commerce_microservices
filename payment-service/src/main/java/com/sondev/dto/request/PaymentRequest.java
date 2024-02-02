package com.sondev.dto.request;

import com.sondev.dto.AbstractDto;
import com.sondev.entity.PaymentMethod;
import com.sondev.entity.PaymentStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

}