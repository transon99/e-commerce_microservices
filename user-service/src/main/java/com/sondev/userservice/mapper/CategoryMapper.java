package com.sondev.userservice.mapper;

import com.sondev.userservice.dto.request.CategoryRequest;
import com.sondev.userservice.dto.response.CategoryDTO;
import com.sondev.userservice.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{

    Category reqToEntity(CategoryRequest categoryRequest);

}