package com.sondev.orderservice.service;

import com.sondev.common.response.ResponseDTO;
import com.sondev.orderservice.dto.request.AddToCartRequest;
import com.sondev.orderservice.dto.request.CartRequest;
import com.sondev.orderservice.dto.response.CartDto;
import com.sondev.orderservice.dto.response.ProductDto;

import java.util.List;

public interface CartService {

    String createCart(CartRequest cartRequest);

    List<CartDto> findAllCarts();

    CartDto findCartById(String id);

//    ResponseDTO updateCategory(Map<String, Object> fields, String id);

    String deleteCartById(String id);

    ProductDto addToCart(AddToCartRequest addToCartRequest, String token);

}
