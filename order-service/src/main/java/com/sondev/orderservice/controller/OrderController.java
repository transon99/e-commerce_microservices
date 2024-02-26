package com.sondev.orderservice.controller;

import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.ManageOrderStatus;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.request.UpdateOrderRequest;
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

@RequestMapping("/orders")
@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createOrder(@RequestBody @Validated OrderRequest orderRequest) {
        log.info("*** OrderDto, controller; create order *");
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Create order successful !!",
                orderService.createOrder(orderRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find order successful !!",
                orderService.findOrderById(id)));
    }

    @GetMapping("/current")
    public ResponseEntity<ResponseMessage> getOrderOfCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find orders successful !!",
                orderService.getOrderOfCurrentUser(token)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getAllOrder() {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get all order successful !!", orderService.getAllOrder()));
    }

    @GetMapping("/filter-paginate")
    public ResponseEntity<ResponseMessage> getOrders(
            @RequestParam Integer offset,
            @RequestParam Integer pageSize) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get orders successful !!", orderService.getOrders(offset, pageSize)));
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseMessage> getAllByStatus() {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get orders successful !!", orderService.getAllByStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> cancelOrder(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Cancel order successful !!", orderService.cancelOrder(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @RequestBody ManageOrderStatus manageOrderStatus, @PathVariable String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update order successful !!", orderService.update(manageOrderStatus,id)));
    }

    @PutMapping("/change-status")
    public ResponseEntity<ResponseMessage> changeStatus(
            @RequestBody UpdateOrderRequest status) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update order successful !!", orderService.changeStatusEvent(status)));
    }

}
