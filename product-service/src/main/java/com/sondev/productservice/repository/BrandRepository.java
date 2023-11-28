package com.sondev.productservice.repository;

import com.sondev.productservice.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Page<Brand>  findByNameContainingIgnoreCase (String name, Pageable page);
}