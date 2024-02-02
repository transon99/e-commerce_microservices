package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.BannerRequest;
import com.sondev.productservice.dto.response.BannerDto;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    String create(BannerRequest bannerRequest);

    BannerDto findById(String id);

    PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr);

    BannerDto update(MultipartFile file, String data, String id) throws JsonProcessingException;

    String deleteById(String id);

}
