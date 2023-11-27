package com.sondev.orderservice.service.impl;

import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.response.OrderDto;
import com.sondev.orderservice.entity.Orders;
import com.sondev.orderservice.mapper.OrderMapper;
import com.sondev.orderservice.repository.OrderRepository;
import com.sondev.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public String createOrder(OrderRequest orderRequest) {
        Orders orderEntity = orderMapper.reqToEntity(orderRequest);
        return orderMapper.toDto(orderRepository.save(orderEntity)).getId();
    }

    @Override
    public OrderDto findOrderById(String id) {
        return null;
    }

    @Override
    public PagingData getOrders(String searchText, Integer offset, Integer pageSize, String sortStr) {
        return null;
    }

    @Override
    public OrderDto updateOrder(Map<String, Object> fields, String id) {
        return null;
    }

    @Override
    public String deleteOrderById(String id) {
        return null;
    }

}
