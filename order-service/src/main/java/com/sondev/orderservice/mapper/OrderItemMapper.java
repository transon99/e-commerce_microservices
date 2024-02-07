package com.sondev.orderservice.mapper;

import com.sondev.orderservice.dto.request.OrderItemRequest;
import com.sondev.orderservice.dto.response.OrderItemDto;
import com.sondev.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDto, OrderItem> {

    OrderItem reqToEntity(OrderItemRequest orderItemRequest);

    Set<OrderItem> reqToEntity(Set<OrderItemRequest> orderItemRequests);

}