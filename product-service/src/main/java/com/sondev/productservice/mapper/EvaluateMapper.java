package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.response.EvaluateDto;
import com.sondev.productservice.entity.Evaluate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluateMapper extends EntityMapper<EvaluateDto, Evaluate>{

}