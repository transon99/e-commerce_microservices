package com.sondev.productservice.repository;

import com.sondev.productservice.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluateRepository extends JpaRepository<Rating, String> {

    Page<Rating> findByProductId(Pageable pageable, String productId);

    Page<Rating> findByProductIdAndUserId(Pageable pageable, String productId, String userId);

}
