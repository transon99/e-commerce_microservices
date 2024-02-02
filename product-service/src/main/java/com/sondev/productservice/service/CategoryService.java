package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.CategoryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    String createCategory(CategoryRequest categoryRequest);

    PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDto findCategoryById(String id);

    List<CategoryDto> getBaseCategories();

    CategoryDto updateCategory(List<MultipartFile> file, MultipartFile iconFile, String data, String id)
            throws JsonProcessingException, IllegalAccessException;

    String deleteCategoryById(String id);

    List<CategoryDto> getAll();

}
