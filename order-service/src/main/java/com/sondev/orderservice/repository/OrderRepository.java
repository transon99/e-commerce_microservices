package com.sondev.orderservice.repository;

import com.sondev.orderservice.entity.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Orders, String> {

}