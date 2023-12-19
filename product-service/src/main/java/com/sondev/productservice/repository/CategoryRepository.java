package com.sondev.productservice.repository;

import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category>  findByNameContainingIgnoreCase (String name, Pageable page);
    List<Category> findByParentCatId(String id);
}