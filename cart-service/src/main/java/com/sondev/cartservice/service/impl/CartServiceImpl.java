package com.sondev.cartservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sondev.cartservice.dto.request.AddToCartRequest;
import com.sondev.cartservice.dto.request.CartRequest;
import com.sondev.cartservice.dto.response.CartDto;
import com.sondev.cartservice.dto.response.ProductDto;
import com.sondev.cartservice.entity.Cart;
import com.sondev.cartservice.entity.CartItem;
import com.sondev.cartservice.feignclient.ProductClient;
import com.sondev.cartservice.mapper.CartMapper;
import com.sondev.cartservice.repository.CartRepository;
import com.sondev.cartservice.service.CartService;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    final ObjectMapper mapper = new ObjectMapper();
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
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        return cartMapper.toDto(cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find cart with id " + id)));
    }

    @Override
    public String deleteCartById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        cartRepository.deleteById(id);
        return id;
    }

    @Override
    public ProductDto addToCart(AddToCartRequest addToCartRequest, String token) {
        ResponseMessage productResponse = productClient.findById(token, addToCartRequest.getProductId()).getBody();
        assert productResponse != null;
        if (productResponse.getData() != null){
            ProductDto productDto = mapper.convertValue(productResponse.getData(), ProductDto.class);
            CartItem cartItem = CartItem.builder()
                    .quantity(addToCartRequest.getQuantity())
                    .productId(productDto.getId())
                    .build();
        }

        return null;
    }

}
