package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.response.CategoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    String createCategory(String data, List<MultipartFile> file) throws JsonProcessingException;

    PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDTO findCategoryById(String id);

    CategoryDTO updateCategory(Map<String, Object> fields, String id);

    String deleteCategoryById(String id);
}
