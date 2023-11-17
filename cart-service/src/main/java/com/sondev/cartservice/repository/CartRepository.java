package com.sondev.cartservice.repository;


import com.sondev.cartservice.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
}