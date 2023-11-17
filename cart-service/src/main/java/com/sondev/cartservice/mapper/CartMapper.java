package com.sondev.cartservice.mapper;


import com.sondev.cartservice.dto.request.CartRequest;
import com.sondev.cartservice.dto.response.CartDto;
import com.sondev.cartservice.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDto, Cart> {
    Cart reqToEntity(CartRequest cartRequest);
}