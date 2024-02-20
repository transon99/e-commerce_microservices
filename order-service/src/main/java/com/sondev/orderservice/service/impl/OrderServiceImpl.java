package com.sondev.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.JwtUtils;
import com.sondev.orderservice.dto.request.OrderItemRequest;
import com.sondev.orderservice.dto.request.OrderRequest;
import com.sondev.orderservice.dto.request.PaymentRequest;
import com.sondev.orderservice.dto.response.CountOrderByStatusResponse;
import com.sondev.orderservice.dto.response.OrderDto;
import com.sondev.orderservice.dto.response.ProductDto;
import com.sondev.orderservice.entity.Order;
import com.sondev.orderservice.entity.PaymentMethod;
import com.sondev.orderservice.entity.PaymentStatus;
import com.sondev.orderservice.entity.Status;
import com.sondev.orderservice.feignclient.CartClient;
import com.sondev.orderservice.feignclient.PaymentClient;
import com.sondev.orderservice.feignclient.ProductClient;
import com.sondev.orderservice.mapper.OrderItemMapper;
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
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest, String token) {

//        double totalPrice = getTotalPrice(orderRequest.getOrderItemRequest(), token);
//
//        Claims claims = JwtUtils.parseClaims(token);
//        String userId = (String) claims.get("userId");
//        Order order = null;
//        if (orderRequest.getPaymentMethod().equals(PaymentMethod.COD.name())) {
//            paymentClient.createPayment(PaymentRequest.builder().paymentStatus(PaymentStatus.NOT_STARTED).paymentMethod(
//                            PaymentMethod.valueOf(orderRequest.getPaymentMethod())).totalPrice(totalPrice).build(),
//                    token);
//
//            order = Order.builder()
//                    .orderDate(new Date())
//                    .userId(userId)
//                    .status(Status.PENDING)
//                    .totalPrice(totalPrice)
//                    .orderItems(orderItemMapper.reqToEntity(orderRequest.getOrderItemRequest()))
//                    .paymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod()))
//                    .build();
//        } else {
//            ResponseMessage cartItemResponse = paymentClient.createPayment(
//                    PaymentRequest.builder().paymentStatus(PaymentStatus.NOT_STARTED).paymentMethod(
//                                    PaymentMethod.valueOf(orderRequest.getPaymentMethod())).totalPrice(totalPrice)
//                            .build(),
//                    token).getBody();
//
//            assert cartItemResponse != null;
//            if (cartItemResponse.getStatus().equals(ResponseStatus.OK)) {
//                order = Order.builder()
//                        .orderDate(new Date())
//                        .userId(userId)
//                        .status(Status.COMPLETED)
//                        .orderItems(orderItemMapper.reqToEntity(orderRequest.getOrderItemRequest()))
//                        .totalPrice(totalPrice)
//                        .paymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod()))
//                        .build();
//            } else {
//                throw new APIException("Something went wrong");
//            }
//
//        }
//
//        assert order != null;
//        return orderMapper.toDto(orderRepository.save(order)).getId();
        return null;
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
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find order with id" + id));

        order.getOrderItems().forEach(
                orderItem-> productClient.reduceQuantity(orderItem.getProductId(), orderItem.getQuantity()));

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
        for (Status status : Status.values()) {
            Integer countStatusOrder = orderRepository.countOrderByStatus(status.name());
            CountOrderByStatusResponse statusOrder = new CountOrderByStatusResponse(status, countStatusOrder);
            responses.add(statusOrder);
        }

        return responses;
    }

    private double getTotalPrice(Set<OrderItemRequest> orderItemDtos, String token) {

        double totalPrice = 0.0;
        for (OrderItemRequest item : orderItemDtos) {

            ResponseMessage productResponse = productClient.findById(token, item.getProductId()).getBody();
            assert productResponse != null;
            if (productResponse.getData() != null) {
                ProductDto productDto = objectMapper.convertValue(productResponse.getData(), ProductDto.class);

                totalPrice += productDto.getPriceUnit() * productDto.getQuantity();
            }

        }
        return totalPrice;
    }

}
