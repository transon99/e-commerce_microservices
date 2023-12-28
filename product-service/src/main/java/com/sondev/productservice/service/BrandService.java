package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.response.BrandDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BrandService {

    String create(List<MultipartFile> files,String data) throws JsonProcessingException;

    PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr);

    BrandDTO findById(String id);

    BrandDTO update(Map<String, Object> fields, String id);

    String deleteById(String id);

    List<BrandDTO> getAll();
}
