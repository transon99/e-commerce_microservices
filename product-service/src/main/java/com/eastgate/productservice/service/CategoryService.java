package com.eastgate.productservice.service;

import com.eastgate.productservice.dto.response.CategoryDTO;
import com.eastgate.productservice.dto.request.CategoryRequest;

import java.util.Map;

public interface CategoryService {

    CategoryDTO createCategory(CategoryRequest categoryRequest);

    CategoryDTO findById(String id);

    CategoryDTO updateCategory(Map<String, Object> fields, String id);

    String deleteById(String id);
}
