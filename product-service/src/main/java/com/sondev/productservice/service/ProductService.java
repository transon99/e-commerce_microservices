package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.common.response.ResponseDTO;
import com.sondev.productservice.dto.response.ProductDto;

import java.util.Map;

public interface ProductService {

    String createProduct(ProductRequest productRequest);

    ProductDto findProductById(String id);

    PagingData getProducts(String searchText, Integer offset, Integer pageSize, String sortStr);

    ProductDto updateProduct(Map<String, Object> fields, String id);

    String deleteProductById(String id);

    void reduceQuantity(String productId, Integer quantity);

}
