package com.sondev.productservice.service;

import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.common.response.ResponseDTO;

import java.util.Map;

public interface ProductService {

    ResponseDTO createProduct(ProductRequest productRequest);

    ResponseDTO findProductById(String id);

    ResponseDTO getProducts(String searchText, Integer offset, Integer pageSize, String sortStr);

    ResponseDTO updateProduct(Map<String, Object> fields, String id);

    ResponseDTO deleteProductById(String id);

}
