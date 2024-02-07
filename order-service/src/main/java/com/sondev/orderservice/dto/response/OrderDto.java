package com.sondev.orderservice.dto.response;

import com.sondev.orderservice.dto.AbstractDto;
import com.sondev.orderservice.entity.PaymentMethod;
import com.sondev.orderservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractDto<String> {

    private String id;

    private Date orderDate;

    private double totalPrice;

    private String paymentMethodId;

    private Status status;

    private PaymentMethod paymentMethod;

    private String userId;

    private List<OrderItemDto> orderItemDtoList;
}