package com.sondev.cartservice.service;

import com.sondev.cartservice.dto.request.AddToCartRequest;
import com.sondev.cartservice.dto.request.CartRequest;
import com.sondev.cartservice.dto.response.CartDto;
import com.sondev.cartservice.dto.response.ProductDto;
import com.sondev.common.response.ResponseDTO;


import java.util.List;

public interface CartService {

    String createCart(CartRequest cartRequest);

    List<CartDto> findAllCarts();

    CartDto findCartById(String id);

//    ResponseDTO updateCategory(Map<String, Object> fields, String id);

    String deleteCartById(String id);

    String addToCart(AddToCartRequest addToCartRequest, String token);

}
