package com.sondev.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.JwtUtils;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.response.CartDto;
import com.sondev.orderservice.dto.response.CartItemDto;
import com.sondev.orderservice.dto.response.CountOrderByStatusResponse;
import com.sondev.orderservice.dto.response.OrderDto;
import com.sondev.orderservice.entity.Order;
import com.sondev.orderservice.entity.Status;
import com.sondev.orderservice.feignclient.CartClient;
import com.sondev.orderservice.feignclient.ProductClient;
import com.sondev.orderservice.mapper.OrderMapper;
import com.sondev.orderservice.repository.OrderRepository;
import com.sondev.orderservice.service.OrderService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest, String token) {
        CartDto cartDto = new CartDto();

        ResponseMessage cartResponse = cartClient.getCartByUser(token).getBody();
        assert cartResponse != null;
        if (cartResponse.getData() != null) {
            cartDto = objectMapper.convertValue(cartResponse.getData(), CartDto.class);

        }

        Claims claims = JwtUtils.parseClaims(token);
        String userId = (String) claims.get("userId");
        Order order = Order.builder()
                .orderDate(new Date())
                .cartId(orderRequest.getCartId())
                .userId(userId)
                .status(Status.PENDING)
                .isAccept(false)
                .totalPrice(cartDto.getTotalPrice())
                .paymentMethodId(orderRequest.getPaymentMethodId())
                .build();

        return orderMapper.toDto(orderRepository.save(order)).getId();
    }

    @Override
    public OrderDto findOrderById(String id) {
        return null;
    }

    @Override
    public PagingData getOrders(Integer offset, Integer pageSize) {

        Page<Order> orderPageEntity;
        Pageable pageable = PageRequest.of(offset, pageSize);

        orderPageEntity = orderRepository.findAll(pageable);

        Page<OrderDto> categoryDTOPage = orderPageEntity.map(orderMapper::toDto);

        return PagingData.builder()
                .data(categoryDTOPage)
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(categoryDTOPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public OrderDto acceptOrder(String id, String token) {
        CartDto cartDto = new CartDto();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find order with id" + id));

        ResponseMessage cartResponse = cartClient.getCartById(token, order.getCartId()).getBody();
        assert cartResponse != null;
        if (cartResponse.getData() != null) {
            cartDto = objectMapper.convertValue(cartResponse.getData(), CartDto.class);
        }

        cartDto.getCartItemIds().forEach(cartItemId -> {
            ResponseMessage cartItemResponse = cartClient.getCartItemById(token, cartItemId).getBody();
            assert cartItemResponse != null;
            if (cartItemResponse.getData() != null) {
                CartItemDto cartItemDto = objectMapper.convertValue(cartItemResponse.getData(), CartItemDto.class);
                productClient.reduceQuantity(cartItemDto.getProductId(), cartItemDto.getQuantity());
            }
        });

        order.setAccept(true);
        order.setStatus(Status.CONFIRMED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public String cancelOrder(String id) {
        orderRepository.deleteById(id);
        return id;
    }

    @Override
    public List<CountOrderByStatusResponse> getAllByStatus() {
        List<CountOrderByStatusResponse> responses = new ArrayList<>();
        for (Status status: Status.values()){
            Integer countStatusOrder = orderRepository.countOrderByStatus(status.name());
            CountOrderByStatusResponse statusOrder = new CountOrderByStatusResponse(status,countStatusOrder);
            responses.add(statusOrder);
        }

        return responses;
    }

}
