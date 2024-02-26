package com.sondev.orderservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.orderservice.dto.request.ManageOrderStatus;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.request.UpdateOrderRequest;
import com.sondev.orderservice.dto.response.CountOrderByStatusResponse;
import com.sondev.orderservice.dto.response.OrderDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OrderService {

    String createOrder(OrderRequest orderRequest);

    OrderDto findOrderById(String id);

    PagingData getOrders( Integer offset, Integer pageSize);

    OrderDto changeStatusEvent(UpdateOrderRequest updateOrderRequest);

    OrderDto update(ManageOrderStatus manageOrderStatus, String id);

    String cancelOrder(String id);

    List<CountOrderByStatusResponse> getAllByStatus();

    List<OrderDto> getOrderOfCurrentUser(String token);

    List<OrderDto> getAllOrder();
}
