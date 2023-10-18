package com.sondev.userservice.mapper;

import com.sondev.userservice.dto.request.ProductRequest;
import com.sondev.userservice.dto.response.ProductDto;
import com.sondev.userservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {

    Product reqToEntity(ProductRequest productRequest);

}