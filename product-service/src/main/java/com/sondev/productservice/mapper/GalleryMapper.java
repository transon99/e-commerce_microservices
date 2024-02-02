package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.ImageRequest;
import com.sondev.productservice.dto.response.ImageDto;
import com.sondev.productservice.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GalleryMapper extends EntityMapper<ImageDto, Image>{

    Image reqToEntity(ImageRequest imageRequest);

}