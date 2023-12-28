package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.GalleryDTO;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.entity.Gallery;
import com.sondev.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
    @Named("mappingImageUrls")
    default List<GalleryDTO> mappingImageUrls(List<Gallery> galleryList) {
        return galleryList.stream().map(gallery -> GalleryDTO.builder()
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