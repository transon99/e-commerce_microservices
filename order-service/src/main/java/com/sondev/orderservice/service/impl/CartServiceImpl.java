package com.sondev.orderservice.service.impl;

import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.CartRequest;
import com.sondev.orderservice.entity.Cart;
import com.sondev.orderservice.mapper.CartMapper;
import com.sondev.orderservice.repository.CartRepository;
import com.sondev.orderservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;

    @Override
    public ResponseDTO createCart(CartRequest cartRequest) {
        Cart cartEntity = cartMapper.reqToEntity(cartRequest);
        return Utils.getResponseSuccess(cartMapper.toDto(cartRepository.save(cartEntity)), "Successfully!!!");
    }

    @Override
    public ResponseDTO findAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (CollectionUtils.isEmpty(carts)) {
            throw new NotFoundException("Can't find any cart");
        }
        return Utils.getResponseSuccess(cartMapper.toDto(cartRepository.findAll()), "Successfully!!!");
    }

    @Override
    public ResponseDTO findCartById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        return Utils.getResponseSuccess(cartMapper.toDto(cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find cart with id " + id))),"Successfully!!!");
    }

    @Override
    public ResponseDTO deleteCartById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");

        cartRepository.deleteById(id);
        return Utils.getResponseSuccess(id,"Successfully!!!");
    }

}
