package com.sondev.mapper;

import com.sondev.dto.request.PaymentRequest;
import com.sondev.dto.response.PaymentDto;
import com.sondev.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDto, Payment> {
    Payment reqToEntity(PaymentRequest paymentRequest);

}