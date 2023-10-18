package com.sondev.productservice.service;

import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.common.response.ResponseDTO;

import java.util.Map;

public interface CategoryService {

    ResponseDTO createCategory(CategoryRequest categoryRequest);

    ResponseDTO findAllCategories();

    ResponseDTO findCategoryById(String id);

    ResponseDTO updateCategory(Map<String, Object> fields, String id);

    ResponseDTO deleteCategoryById(String id);
}
