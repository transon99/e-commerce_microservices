package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.response.ImageDto;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.dto.response.ReviewDto;
import com.sondev.productservice.entity.Image;
import com.sondev.productservice.entity.Product;
import com.sondev.productservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDto, Review>{

    @Named("mappingProduct")
    default ProductDto mappingProduct(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    @Mapping(target = "product", source = "product", qualifiedByName = "mappingProduct")
    ReviewDto toDto(Review review);

}