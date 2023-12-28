package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.adapter.CloudinaryService;
import com.sondev.productservice.dto.request.CategoryRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    private final GalleryMapper galleryMapper;

    @Override
    public Gallery create(MultipartFile file, String data) throws JsonProcessingException {
        GalleryRequest galleryRequest = objectMapper.readValue(data, GalleryRequest.class);
        Gallery entity = galleryMapper.reqToEntity(galleryRequest);
        return null;
    }

    @Override
    public CategoryDTO findById(String id) {

        return null;
    }

    @Override
    public PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr) {
        return null;
    }

    @Override
    public CategoryDTO update(MultipartFile file, String data, String id) {
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
