package com.sondev.cartservice.repository;

import com.sondev.cartservice.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem,String> {
    CartItem findByIdAndUserId(String id, String userId);
}
