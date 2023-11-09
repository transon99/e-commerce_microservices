package com.sondev.orderservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.ResponseMessage;
import com.sondev.orderservice.dto.request.AddToCartRequest;
import com.sondev.orderservice.dto.request.CartRequest;
import com.sondev.orderservice.dto.response.CartDto;
import com.sondev.orderservice.dto.response.ProductDto;
import com.sondev.orderservice.entity.Cart;
import com.sondev.orderservice.feignclient.ProductClient;
import com.sondev.orderservice.mapper.CartMapper;
import com.sondev.orderservice.repository.CartRepository;
import com.sondev.orderservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    public String createCart(CartRequest cartRequest) {
        Cart cartEntity = cartMapper.reqToEntity(cartRequest);
        return cartMapper.toDto(cartRepository.save(cartEntity)).getId();
    }

    @Override
    public List<CartDto> findAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (CollectionUtils.isEmpty(carts)) {
            throw new NotFoundException("Can't find any cart");
        }
        return cartMapper.toDto(cartRepository.findAll());
    }

    @Override
    public CartDto findCartById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        return cartMapper.toDto(cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find cart with id " + id)));
    }

    @Override
    public String deleteCartById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        cartRepository.deleteById(id);
        return id;
    }

    @Override
    public ProductDto addToCart(AddToCartRequest addToCartRequest, String token) {
        ResponseMessage responseMessage =  productClient.findById(token,addToCartRequest.getProductId()).getBody();
        Gson gson = new Gson();
        String responseEntityBodyInString = Objects.requireNonNull(responseMessage.getData().toString());
        ProductDto productDto;
        try {
            productDto = gson.fromJson(responseEntityBodyInString, ProductDto.class);
        } catch (Exception e) {
            throw new APIException( "Can not get response from data cloud service");
        }
        return productDto;
    }

}
