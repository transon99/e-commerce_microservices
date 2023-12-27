package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {

    String createProduct(List<MultipartFile> files,String data) throws JsonProcessingException;

    ProductDto findProductById(String id);

    PagingData getProducts(String searchText, Integer offset, Integer pageSize, String sortStr);

    PagingData findProductsByCategoryAndBrand(Integer offset, Integer pageSize, String categoryId, String brandId);

    ProductDto updateProduct(List<MultipartFile> files,String data, String id) throws JsonProcessingException;

    String deleteProductById(String id);

    void reduceQuantity(String productId, Integer quantity);

}
