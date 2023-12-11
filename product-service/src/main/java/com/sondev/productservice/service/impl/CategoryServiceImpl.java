package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.response.PagingData;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.common.utils.commonUtils;
import com.sondev.productservice.adapter.CloudinaryService;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Gallery;
import com.sondev.productservice.exceptions.MissingInputException;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.mapper.CategoryMapper;
import com.sondev.productservice.repository.CategoryRepository;
import com.sondev.productservice.service.CategoryService;
import com.sondev.productservice.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final GalleryService galleryService;
    private final ObjectMapper objectMapper;

    private final CategoryMapper categoryMapper;

    public String createCategory(String data, List<MultipartFile> files) throws JsonProcessingException {
        CategoryRequest categoryRequest = objectMapper.readValue(data, CategoryRequest.class);

        Category entity = categoryMapper.reqToEntity(categoryRequest);
        List<Gallery> galleries = files.stream().map(file -> {
            Map result = cloudinaryService.uploadFile(file);
            String imageUrl = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");
            return Gallery.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
        }).toList();
        entity.setImageUrls(galleries);
        return categoryMapper.toDto(categoryRepository.save(entity)).getId();
    }

    public PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Category> productPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (searchText.isEmpty()) {
            productPage = categoryRepository.findAll(pageable);
        } else {
            productPage = categoryRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        return PagingData.builder()
                .data(productPage)
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(productPage.getTotalElements())
                .build();
    }

    public CategoryDTO findCategoryById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id)));
    }

    public String deleteCategoryById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");

        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id));

        categoryRepository.deleteById(id);

        category.getImageUrls().forEach(image ->
                cloudinaryService.deleteFile(image.getPublicId())
        );

        return id;
    }

    @Override
    public List<CategoryDTO> getAll() {

        return categoryMapper.toDto(categoryRepository.findAll());
    }

    public CategoryDTO updateCategory(List<MultipartFile> files, String data, String id) throws JsonProcessingException, IllegalAccessException {

        Category currentCategory = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find category with id " + id));

        CategoryRequest categoryRequest = objectMapper.readValue(data, CategoryRequest.class);
        List<Gallery> galleries;
        if (files != null) {
            List<Gallery> imageList = currentCategory.getImageUrls();
            imageList.forEach(image -> galleryService.deleteById(image.getId()));
            galleries = files.stream().map(file -> {
                Map result = cloudinaryService.uploadFile(file);
                String imageUrl = (String) result.get("secure_url");
                String publicId = (String) result.get("public_id");
                return Gallery.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
            }).toList();
        } else {
            galleries = currentCategory.getImageUrls();
        }

        Category newCategory = Category.builder()
                .id(currentCategory.getId())
                .name(categoryRequest.getName())
                .imageUrls(galleries)
                .build();


        return categoryMapper.toDto(categoryRepository.save(newCategory));

    }

}
