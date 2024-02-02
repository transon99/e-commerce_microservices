package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.response.CategoryDto;
import com.sondev.productservice.entity.Image;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
    Image create(MultipartFile file, String data) throws JsonProcessingException;

    CategoryDto findById(String id);

    PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDto update(MultipartFile file, String data, String id);

    String deleteById(String id);
}
