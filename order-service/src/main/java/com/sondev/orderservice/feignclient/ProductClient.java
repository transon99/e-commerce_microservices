package com.sondev.orderservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-service", url = "http://localhost:8081/api/v1/products")
public interface ProductClient {
    @GetMapping("/{id}")
    ResponseEntity findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);
}
