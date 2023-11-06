package com.sondev.orderservice.controller;

import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.AddToCartRequest;
import com.sondev.orderservice.dto.request.CartRequest;
import com.sondev.orderservice.dto.response.CartDto;
import com.sondev.orderservice.dto.response.ProductDto;
import com.sondev.orderservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/carts")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(cartService.deleteCartById(id));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<ProductDto> addToCart(@RequestBody AddToCartRequest addToCartRequest, @RequestHeader("Authorization") String token ) {
        return ResponseEntity.ok().body(cartService.addToCart(addToCartRequest, token));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity update(@RequestBody Map<String, Object> fields, @PathVariable(name = "id") String id) {
//        return Utils.checkStatusCodeAndResponse(cartService.updateCategory(fields, id));
//    }

}
