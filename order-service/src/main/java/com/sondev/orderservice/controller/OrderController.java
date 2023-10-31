package com.sondev.orderservice.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/products")
@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody @Validated OrderRequest orderRequest) {
        log.info("*** OrderDto, controller; create order *");
        return Utils.checkStatusCodeAndResponse(orderService.createOrder(orderRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(orderService.findOrderById(id));
    }

    @GetMapping()
    public ResponseEntity getOrders(@RequestParam String searchText,
                                      @RequestParam Integer offset,
                                      @RequestParam Integer pageSize,
                                      @RequestParam String sortStr) {
        return Utils.checkStatusCodeAndResponse(orderService.getOrders(searchText, offset, pageSize, sortStr));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(orderService.deleteOrderById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Validated Map<String, Object> fields,
                                 @PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(orderService.updateOrder(fields, id));
    }

}
