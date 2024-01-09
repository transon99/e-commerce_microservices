package com.sondev.cartservice.service;

import com.sondev.cartservice.dto.request.AddToCartRequest;
import com.sondev.cartservice.dto.request.CartRequest;
import com.sondev.cartservice.dto.response.CartDto;
import com.sondev.cartservice.dto.response.CartItemDto;

import java.util.List;

public interface CartService {

    String createCart(CartRequest cartRequest);

    List<CartDto> findAllCarts();

    CartDto findCartById(String id);

    CartDto getUserCart(String token);

    String deleteCartById(String id);

    String addToCart(AddToCartRequest addToCartRequest, String token);

    String updateCart(CartRequest cartRequest,String token);

    String removeCartItem(String id, String token);

    CartItemDto findCartItemById(String id);

}
