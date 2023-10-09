package com.eastgate.serviceregisty.configserver.productservice.service;

import com.eastgate.serviceregisty.configserver.productservice.dto.request.CategoryRequest;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryDTO;

public interface CategoryService {

    CategoryDTO createCategory(CategoryRequest categoryRequest);

    CategoryDTO findById(Long id);
}
