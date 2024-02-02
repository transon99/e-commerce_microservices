package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.CategoryDto;
import com.sondev.productservice.dto.response.ImageDto;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category>{

    Category reqToEntity(CategoryRequest categoryRequest);

    @Named("mappingImageUrls")
    default List<ImageDto> mappingImageUrls(List<Image> imageList) {
        return imageList.stream().map(gallery -> ImageDto.builder()
                .id(gallery.getId())
                .thumbnailUrl(gallery.getThumbnailUrl())
                .build()).toList();
    }

    @Named("mappingIconUrl")
    default ImageDto mappingIconUrl(Image image) {
        return  ImageDto.builder()
                .id(image.getId())
                .thumbnailUrl(image.getThumbnailUrl())
                .build();
    }

    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    @Mapping(target = "iconUrl", source = "iconUrl", qualifiedByName = "mappingIconUrl")
    List<CategoryDto> toDto(List<Category> categoryList);
}