package com.sondev.cartservice.controller;

import com.sondev.cartservice.dto.request.AddToCartRequest;
import com.sondev.cartservice.dto.request.CartRequest;
import com.sondev.cartservice.dto.response.CartDto;
import com.sondev.cartservice.dto.response.CartItemDto;
import com.sondev.cartservice.dto.response.ProductDto;
import com.sondev.cartservice.service.CartService;
import com.sondev.common.utils.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequestMapping("/carts")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody @Validated CartRequest cartRequest) {
        return ResponseEntity.ok().body(cartService.createCart(cartRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(cartService.findCartById(id));
    }

    @GetMapping()
    public ResponseEntity<List<CartDto>> findAll() {
        return ResponseEntity.ok().body(cartService.findAllCarts());
    }

    @GetMapping("/user")
    public ResponseEntity<CartDto> findByUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(cartService.getUserCart(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(cartService.deleteCartById(id));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest addToCartRequest,
                                            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(cartService.addToCart(addToCartRequest, token));
    }

    @PutMapping("")
    public ResponseEntity<String> update(@RequestBody CartRequest cartRequest,
                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(cartService.updateCart(cartRequest, token));
    }

    @GetMapping("/cart-item/{id}")
    public ResponseEntity<CartItemDto> findCartItemById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(cartService.findCartItemById(id));
    }

    @DeleteMapping("/cart-item/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable(name = "id") String id,
                                                 @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(cartService.removeCartItem(id, token));
    }

}
