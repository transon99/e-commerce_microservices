package com.sondev.orderservice.feignclient;

import com.sondev.common.response.ResponseMessage;
import com.sondev.orderservice.dto.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://localhost:8081/api/v1/payment-service/payments")
public interface PaymentClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<ResponseMessage> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);

    @GetMapping()
    ResponseEntity<ResponseMessage> findAll(@RequestHeader(name = "Authorization") String token);

    @PostMapping
    ResponseEntity<ResponseMessage> checkout(@RequestBody @Validated PaymentRequest paymentRequest, @RequestHeader("Authorization") String token);
}
