package com.sondev.productservice.repository;

import com.sondev.productservice.entity.Evaluate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluateRepository extends JpaRepository<Evaluate, String> {

    Page<Evaluate> findByProductId(Pageable pageable, String productId);

    Page<Evaluate> findByProductIdAndUserId(Pageable pageable, String productId, String userId);

}
