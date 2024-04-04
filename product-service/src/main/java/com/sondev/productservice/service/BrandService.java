package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.dto.response.BrandDto;

import java.util.List;
import java.util.Map;

public interface BrandService {

    String create(BrandRequest brandRequest);

    PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr);

    BrandDto findById(String id);

    BrandDto update(BrandRequest brandRequest, String id);

    String deleteById(String id);

    List<BrandDto> getAll();
}
