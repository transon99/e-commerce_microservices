package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.dto.response.BrandDTO;
import com.sondev.productservice.dto.response.CategoryDTO;

import java.util.Map;

public interface BrandService {

    String create(BrandRequest brandRequest);

    PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr);

    BrandDTO findById(String id);

    BrandDTO update(Map<String, Object> fields, String id);

    String deleteById(String id);
}
