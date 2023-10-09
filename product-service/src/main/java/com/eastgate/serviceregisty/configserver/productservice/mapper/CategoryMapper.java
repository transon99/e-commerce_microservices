package com.eastgate.serviceregisty.configserver.productservice.mapper;

import com.eastgate.serviceregisty.configserver.productservice.dto.request.CategoryRequest;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryDTO;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{
    Category reqToEntity(CategoryRequest categoryRequest);
}