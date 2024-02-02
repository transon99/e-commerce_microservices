package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.dto.response.BrandDto;
import com.sondev.productservice.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDto, Brand>{

    Brand reqToEntity(BrandRequest brandRequest);

}