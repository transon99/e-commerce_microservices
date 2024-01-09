package com.sondev.orderservice.feignclient;

import com.sondev.common.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "http://localhost:8081/api/v1/product-service/products")
public interface ProductClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<ResponseMessage> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);

    @GetMapping()
    ResponseEntity<ResponseMessage> findAll(@RequestHeader(name = "Authorization") String token);

    @PutMapping("/reduce-quantity/{id}")
    ResponseEntity<ResponseMessage> reduceQuantity(
            @PathVariable("id") String productId,
            @RequestParam Integer quantity
    );
}
