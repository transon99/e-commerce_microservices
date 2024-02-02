package com.sondev.orderservice.mapper;

import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.response.OrderDto;
import com.sondev.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDto, Order> {

    Order reqToEntity(OrderRequest orderRequest);

}