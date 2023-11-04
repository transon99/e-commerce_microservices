package com.sondev.orderservice.controller;

import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.AddToCartRequest;
import com.sondev.orderservice.dto.request.CartRequest;
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

@RequestMapping("/api/v1/carts")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity createCart(@RequestBody @Validated CartRequest cartRequest) {
        return Utils.checkStatusCodeAndResponse(cartService.createCart(cartRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(cartService.findCartById(id));
    }

    @GetMapping()
    public ResponseEntity findAll() {
        return Utils.checkStatusCodeAndResponse(cartService.findAllCarts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(cartService.deleteCartById(id));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity addToCart(@RequestBody AddToCartRequest addToCartRequest,@RequestHeader("Authorization") String token ) {
        return Utils.checkStatusCodeAndResponse(cartService.addToCart(addToCartRequest, token));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity update(@RequestBody Map<String, Object> fields, @PathVariable(name = "id") String id) {
//        return Utils.checkStatusCodeAndResponse(cartService.updateCategory(fields, id));
//    }

}
