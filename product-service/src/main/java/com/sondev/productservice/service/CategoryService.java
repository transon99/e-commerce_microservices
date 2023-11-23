package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.CategoryDTO;

import java.util.Map;

public interface CategoryService {

    String createCategory(CategoryRequest categoryRequest);

    PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDTO findCategoryById(String id);

    CategoryDTO updateCategory(Map<String, Object> fields, String id);

    String deleteCategoryById(String id);
}
