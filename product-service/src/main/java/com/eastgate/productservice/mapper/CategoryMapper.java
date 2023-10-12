package com.eastgate.productservice.mapper;

import com.eastgate.productservice.dto.request.CategoryRequest;
import com.eastgate.productservice.dto.response.CategoryDTO;
import com.eastgate.productservice.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{

    Category reqToEntity(CategoryRequest categoryRequest);

}