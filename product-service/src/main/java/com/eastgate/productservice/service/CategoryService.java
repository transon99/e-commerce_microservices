package com.eastgate.productservice.service;

import com.eastgate.productservice.dto.response.CategoryDTO;
import com.eastgate.productservice.dto.request.CategoryRequest;
import com.eastgate.response.ResponseDTO;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseDTO createCategory(CategoryRequest categoryRequest);

    ResponseDTO findAllCategories();

    ResponseDTO findCategoryById(String id);

    ResponseDTO updateCategory(Map<String, Object> fields, String id);

    ResponseDTO deleteCategoryById(String id);
}
