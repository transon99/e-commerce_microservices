package com.sondev.feignclient;

import com.sondev.common.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", url = "http://localhost:8081/api/v1/orders")
public interface OrderClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<ResponseMessage> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);

    @GetMapping()
    ResponseEntity findAll(@RequestHeader(name = "Authorization") String token);

}
