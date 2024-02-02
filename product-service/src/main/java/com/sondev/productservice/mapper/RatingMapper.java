package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.response.RatingDto;
import com.sondev.productservice.entity.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDto, Rating>{

}