package com.eastgate.productservice.mapper;

import com.eastgate.productservice.dto.request.ProductRequest;
import com.eastgate.productservice.dto.response.ProductDto;
import com.eastgate.productservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {

    Product reqToEntity(ProductRequest productRequest);

}