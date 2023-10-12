package com.eastgate.productservice.service;

import com.eastgate.productservice.dto.request.ProductRequest;
import com.eastgate.productservice.dto.response.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductDto createProduct(ProductRequest productRequest);

    ProductDto findProductById(String id);

    List<ProductDto> getProducts(String searchText, Integer offset, Integer pageSize, String sortStr);

    ProductDto updateProduct(Map<String, Object> fields, String id);

    String deleteProductById(String id);

}
