package com.sondev.orderservice.repository;

import com.sondev.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Integer countOrderByStatus(String status);
}