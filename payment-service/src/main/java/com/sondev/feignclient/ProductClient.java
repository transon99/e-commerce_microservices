package com.sondev.feignclient;

import com.sondev.common.response.ResponseMessage;
import com.sondev.event.ReduceQtyEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "http://localhost:8080/api/v1/product-service/products")
public interface ProductClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<ResponseMessage> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);

    @PutMapping("/reduce-quantity")
    ResponseEntity<ResponseMessage> reduceQuantity(
            @RequestBody ReduceQtyEvent reduceQtyEvent
    );
}
