package com.sondev.productservice.service.impl;

import com.sondev.productservice.adapter.CloudinaryService;
import com.sondev.productservice.dto.request.GalleryRequest;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Gallery;
import com.sondev.productservice.exceptions.MissingInputException;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.mapper.GalleryMapper;
import com.sondev.productservice.repository.GalleryRepository;
import com.sondev.productservice.repository.ProductRepository;
import com.sondev.productservice.service.GalleryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final CloudinaryService cloudinaryService;

    private final GalleryMapper galleryMapper;

    @Override
    public Gallery create(GalleryRequest galleryRequest) {
        Gallery gallery = galleryMapper.reqToEntity(galleryRequest);
        //        gallery.setProduct(productRepository.findById(galleryRequest.getProductId()).orElseThrow(
        //                () -> new NotFoundException("Can't find product with id: " + galleryRequest.getProductId())));
        return galleryRepository.save(gallery);
    }

    @Override
    public String addGalleryToProduct(String galleryId, String productId) {
        Gallery currentGallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new NotFoundException("Can't find gallery with id: " + galleryId));
        return null;
    }

    @Override
    public CategoryDTO findById(String id) {
        return null;
    }

    @Override
    public CategoryDTO update(Map<String, Object> fields, String id) {
        return null;
    }

    @Override
    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");

        }
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find gallery with id " + id));
        galleryRepository.deleteById(id);
        cloudinaryService.deleteFile(gallery.getPublicId());

        return id;
    }

}
