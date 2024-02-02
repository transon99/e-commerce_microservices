package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.ImageDto;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.entity.Image;
import com.sondev.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
    @Named("mappingImageUrls")
    default List<ImageDto> mappingImageUrls(List<Image> imageList) {
        return imageList.stream().map(gallery -> ImageDto.builder()
                .id(gallery.getId())
                .thumbnailUrl(gallery.getThumbnailUrl())
                .build()).toList();
    }

    @Mapping(target = "imageUrls", source = "thumbnailUrls", qualifiedByName = "mappingImageUrls")
    List<ProductDto> toDto(List<Product> productList);

    @Mapping(target = "imageUrls", source = "thumbnailUrls", qualifiedByName = "mappingImageUrls")
    ProductDto toDto(Product product);

    Product reqToEntity(ProductRequest productRequest);

}