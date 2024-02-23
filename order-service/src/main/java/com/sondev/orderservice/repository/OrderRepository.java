package com.sondev.orderservice.repository;

import com.sondev.orderservice.entity.Order;
import com.sondev.orderservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Integer countOrderByStatus(Status status);

    List<Order> findByUserId(String userId);
}