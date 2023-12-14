package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.dto.response.GalleryDTO;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Gallery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{

    Category reqToEntity(CategoryRequest categoryRequest);

    @Named("mappingImageUrls")
    default List<GalleryDTO> mappingImageUrls(List<Gallery> galleryList) {
        return galleryList.stream().map(gallery -> GalleryDTO.builder()
                .id(gallery.getId())
                .thumbnailUrl(gallery.getThumbnailUrl())
                .build()).toList();
    }

    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    List<CategoryDTO> toDto(List<Category> categoryList);
}