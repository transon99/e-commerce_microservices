package com.sondev.orderservice.controller;

import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/orders")
@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createOrder(@RequestBody @Validated OrderRequest orderRequest,
                                                       @RequestHeader("Authorization") String token) {
        log.info("*** OrderDto, controller; create order *");
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create order successful !!",
                orderService.createOrder(orderRequest, token)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Find order successful !!",
                orderService.findOrderById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getOrders(
            @RequestParam Integer offset,
            @RequestParam Integer pageSize) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "get orders successful !!", orderService.getOrders(offset, pageSize)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getAllByStatus() {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "get orders successful !!", orderService.getAllByStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> cancelOrder(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Cancel order successful !!", orderService.cancelOrder(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> acceptOrder(
            @PathVariable(name = "id") String id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Accept order successful !!", orderService.acceptOrder(id, token)));
    }

}
