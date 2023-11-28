package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.BrandDTO;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.entity.Brand;
import com.sondev.productservice.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand>{

    Brand reqToEntity(BrandRequest brandRequest);

}