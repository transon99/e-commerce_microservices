package com.sondev.orderservice.service.impl;

import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.Utils;
import com.sondev.orderservice.dto.request.OrderRequest;
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
    public ResponseDTO createOrder(OrderRequest orderRequest) {
        Orders orderEntity = orderMapper.reqToEntity(orderRequest);
        return Utils.getResponseSuccess(orderMapper.toDto(orderRepository.save(orderEntity)), "Successfully!!!");
    }

    @Override
    public ResponseDTO findOrderById(String id) {
        return null;
    }

    @Override
    public ResponseDTO getOrders(String searchText, Integer offset, Integer pageSize, String sortStr) {
        return null;
    }

    @Override
    public ResponseDTO updateOrder(Map<String, Object> fields, String id) {
        return null;
    }

    @Override
    public ResponseDTO deleteOrderById(String id) {
        return null;
    }

}
