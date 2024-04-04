package com.sondev.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.event.ReduceQtyEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    String createProduct(ProductRequest productRequest);

    ProductDto findProductById(String id);

    PagingData getProducts(String searchText, Integer offset, Integer pageSize, String sortStr);

    PagingData findProductsByCategoryAndBrand(Integer offset, Integer pageSize, String categoryId, String brandId);

    ProductDto updateProduct(List<MultipartFile> files,String data, String id) throws JsonProcessingException;

    String deleteProductById(String id);

    void reduceQuantity(ReduceQtyEvent reduceQtyEvent);

    List<ProductDto> getProductByCategory(String category);
}
