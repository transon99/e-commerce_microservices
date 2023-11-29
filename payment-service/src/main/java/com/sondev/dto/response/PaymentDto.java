package com.sondev.dto.response;

import com.sondev.dto.AbstractDto;
import com.sondev.entity.PaymentMethod;
import com.sondev.entity.PaymentStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto extends AbstractDto<Integer> {

    private String id;
    private String paymentId;
    private String orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;



}