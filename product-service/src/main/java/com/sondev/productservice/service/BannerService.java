package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.response.BannerDto;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.entity.Gallery;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    String create(MultipartFile file, String data) throws JsonProcessingException;

    BannerDto findById(String id);

    PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr);

    BannerDto update(MultipartFile file, String data, String id) throws JsonProcessingException;

    String deleteById(String id);

}
