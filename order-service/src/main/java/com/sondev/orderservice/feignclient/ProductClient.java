package com.sondev.orderservice.feignclient;

import com.sondev.orderservice.dto.response.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "product-service", url = "http://localhost:8081/api/v1/products")
public interface ProductClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<ProductDto> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);

    @GetMapping()
    ResponseEntity findAll(@RequestHeader(name = "Authorization") String token);

}
