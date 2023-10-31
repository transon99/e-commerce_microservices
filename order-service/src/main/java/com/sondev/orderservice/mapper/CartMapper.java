package com.sondev.orderservice.mapper;

import com.sondev.orderservice.dto.request.CartRequest;
import com.sondev.orderservice.dto.response.CartDto;
import com.sondev.orderservice.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDto, Cart>{
    Cart reqToEntity(CartRequest cartRequest);
}