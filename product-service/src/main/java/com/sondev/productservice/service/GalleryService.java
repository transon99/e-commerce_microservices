package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.request.GalleryRequest;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.entity.Gallery;

import java.util.Map;

public interface GalleryService {
    Gallery create(GalleryRequest galleryRequest);

    String addGalleryToProduct(String galleryId, String productId);

    CategoryDTO findById(String id);

    CategoryDTO update(Map<String, Object> fields, String id);

    String deleteById(String id);
}
