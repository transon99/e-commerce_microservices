package com.sondev.orderservice.mapper;

import com.sondev.orderservice.dto.request.OrderItemRequest;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.response.OrderDto;
import com.sondev.orderservice.dto.response.OrderItemDto;
import com.sondev.orderservice.entity.Order;
import com.sondev.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDto, Order> {

    OrderDto toDto(Order order);

    Order reqToEntity(OrderRequest orderRequest);
}