package com.eastgate.serviceregisty.configserver.productservice.mapper;

import com.eastgate.serviceregisty.configserver.productservice.dto.CategoryDto;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {

}