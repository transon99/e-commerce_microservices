package com.sondev.cartservice.mapper;

import com.sondev.cartservice.dto.response.CartItemDto;
import com.sondev.cartservice.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemDto, CartItem> {
}