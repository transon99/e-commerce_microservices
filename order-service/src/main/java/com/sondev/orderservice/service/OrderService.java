package com.sondev.orderservice.service;

import com.sondev.common.response.ResponseDTO;
import com.sondev.orderservice.dto.request.OrderRequest;

import java.util.Map;

public interface OrderService {

    ResponseDTO createOrder(OrderRequest orderRequest);

    ResponseDTO findOrderById(String id);

    ResponseDTO getOrders(String searchText, Integer offset, Integer pageSize, String sortStr);

    ResponseDTO updateOrder(Map<String, Object> fields, String id);

    ResponseDTO deleteOrderById(String id);

}
