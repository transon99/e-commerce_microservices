package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.adapter.CloudinaryService;
import com.sondev.productservice.dto.request.ImageRequest;
import com.sondev.productservice.dto.response.CategoryDto;
import com.sondev.productservice.entity.Image;
import com.sondev.productservice.mapper.GalleryMapper;
import com.sondev.productservice.repository.GalleryRepository;
import com.sondev.productservice.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {

    private final GalleryRepository galleryRepository;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    private final GalleryMapper galleryMapper;

    @Override
    public Image create(MultipartFile file, String data) throws JsonProcessingException {
        ImageRequest imageRequest = objectMapper.readValue(data, ImageRequest.class);
        Image entity = galleryMapper.reqToEntity(imageRequest);
        return null;
    }

    @Override
    public CategoryDto findById(String id) {

        return null;
    }

    @Override
    public PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr) {
        return null;
    }

    @Override
    public CategoryDto update(MultipartFile file, String data, String id) {
        return null;
    }

    @Override
    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");

        }
        Image image = galleryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find gallery with id " + id));
        galleryRepository.deleteById(id);
        cloudinaryService.deleteFile(image.getPublicId());

        return id;
    }

}
