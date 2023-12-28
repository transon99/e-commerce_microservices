package com.sondev.productservice.repository;

import com.sondev.productservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category>  findByNameContainingIgnoreCase (String name, Pageable page);
}