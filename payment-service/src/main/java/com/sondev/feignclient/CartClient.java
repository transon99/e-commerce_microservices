package com.sondev.feignclient;

import com.sondev.common.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "cart-service", url = "http://localhost:8084/api/v1/cart-service/carts")
public interface CartClient {

    @GetMapping(value = "/user")
    ResponseEntity<ResponseMessage> getCartByUser(@RequestHeader(name = "Authorization", required = true) String token);

    @GetMapping(value = "/{id}")
    ResponseEntity<ResponseMessage> getCartById(@RequestHeader(name = "Authorization", required = true) String token,
                                                @PathVariable String id);

    @GetMapping(value = "/cart-item/{id}")
    ResponseEntity<ResponseMessage> getCartItemById(@RequestHeader(name = "Authorization", required = true) String token,
                                                @PathVariable String id);

}
