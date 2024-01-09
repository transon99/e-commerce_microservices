package com.sondev.orderservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.response.OrderDto;

import java.util.Map;

public interface OrderService {

    String createOrder(OrderRequest orderRequest, String token);

    OrderDto findOrderById(String id);

    PagingData getOrders(String searchText, Integer offset, Integer pageSize, String sortStr);

    OrderDto acceptOrder(String id,String token);

    String cancelOrder(String id);

}
