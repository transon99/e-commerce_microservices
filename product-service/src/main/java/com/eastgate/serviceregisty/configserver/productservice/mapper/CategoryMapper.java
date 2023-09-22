package com.eastgate.serviceregisty.configserver.productservice.mapper;

import com.eastgate.serviceregisty.configserver.productservice.dto.request.CategoryRequest;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryResponse;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category reqToEntity(CategoryRequest categoryRequest);
    CategoryResponse entityToResponse(Category category);
}