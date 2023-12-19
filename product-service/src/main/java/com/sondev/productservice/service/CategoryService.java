package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.response.CategoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    String createCategory(String data, List<MultipartFile> file) throws JsonProcessingException;

    PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDTO findCategoryById(String id);

    List<CategoryDTO> getSubCategory(String id);

    CategoryDTO updateCategory(List<MultipartFile> file, String data, String id) throws JsonProcessingException, IllegalAccessException;

    String deleteCategoryById(String id);

    List<CategoryDTO> getAll();
}
