package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.GalleryRequest;
import com.sondev.productservice.dto.response.GalleryDTO;
import com.sondev.productservice.entity.Gallery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GalleryMapper extends EntityMapper<GalleryDTO, Gallery>{

    Gallery reqToEntity(GalleryRequest galleryRequest);

}