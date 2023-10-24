package com.sondev.productservice.service.impl;

import com.sondev.common.constants.ResponseStatusCode;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Product;
import com.sondev.productservice.exceptions.MissingInputException;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.mapper.ProductMapper;
import com.sondev.productservice.repository.ProductRepository;
import com.sondev.productservice.service.ProductService;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseDTO;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.common.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ResponseDTO createProduct(ProductRequest productRequest) {
        Product entity = productMapper.reqToEntity(productRequest);
        return Utils.getResponseSuccess(productMapper.toDto(productRepository.save(entity)),"Successfully!!!");
    }

    public ResponseDTO findProductById(String id) {
        if (id == null){
            log.error("Missing input id");
            throw new MissingInputException("Missing input id");
        }
        return Utils.getResponseSuccess(productMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + id))),"Successfully!!!");
    }

    public ResponseDTO getProducts(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Product> productPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)){
            productPage = productRepository.findAll(pageable);
        }else {
            productPage = productRepository.findByNameContainingIgnoreCase(searchText,pageable);
        }
        return ResponseDTO.builder()
                .data(productMapper.toDto(productPage.toList()))
                .message("Successfully!!!")
                .pagingData(PagingData.builder()
                        .searchText(searchText)
                        .offset(offset)
                        .pageSize(pageSize)
                        .sort(sortStr)
                        .totalRecord(productPage.getTotalElements())
                        .build())
                .responseStatusCode(ResponseStatusCode.OK)
                .build();
    }

    public ResponseDTO updateProduct(Map<String, Object> fields, String id) {
        Product currentProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find category with id" + id));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Category.class, key);
            if (field == null) throw new NullPointerException("Can't find any field");
            field.setAccessible(true);
            ReflectionUtils.setField(field, currentProduct, value);
        });

        return Utils.getResponseSuccess(productMapper.toDto(productRepository.save(currentProduct)),"Successfully!!!");
    }

    public ResponseDTO deleteProductById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        productRepository.deleteById(id);
        return Utils.getResponseSuccess(id,"Successfully!!!");
    }

}
