//package com.sondev.feignclient;
//
//import com.sondev.common.response.ResponseMessage;
//import com.sondev.dto.request.OrderRequest;
//import com.sondev.event.UpdateOrderRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@FeignClient(name = "order-service", url = "http://localhost:8081/api/v1/order-service")
//public interface OrderClient {
//
//    @PutMapping("/change-status/{id}")
//    ResponseEntity<ResponseMessage> changeStatus(
//            @RequestBody UpdateOrderRequest status , @PathVariable String id);
//    @PostMapping
//    ResponseEntity<ResponseMessage> createOrder(@RequestBody @Validated OrderRequest orderRequest);
//
//    @GetMapping(value = "/{id}")
//    ResponseEntity<ResponseMessage> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);
//
//    @GetMapping()
//    ResponseEntity findAll(@RequestHeader(name = "Authorization") String token);
//
//}
