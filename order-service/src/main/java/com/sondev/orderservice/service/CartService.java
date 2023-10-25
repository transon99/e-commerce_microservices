package com.sondev.orderservice.service;

import com.sondev.common.response.ResponseDTO;
import com.sondev.orderservice.dto.request.CartRequest;

public interface CartService {

    ResponseDTO createCart(CartRequest cartRequest);

    ResponseDTO findAllCarts();

    ResponseDTO findCartById(String id);

//    ResponseDTO updateCategory(Map<String, Object> fields, String id);

    ResponseDTO deleteCartById(String id);

}
