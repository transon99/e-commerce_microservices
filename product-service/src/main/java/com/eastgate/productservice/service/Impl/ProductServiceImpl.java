package com.eastgate.productservice.service.Impl;

import com.eastgate.constants.ResponseStatusCode;
import com.eastgate.productservice.dto.request.ProductRequest;
import com.eastgate.productservice.entity.Category;
import com.eastgate.productservice.entity.Product;
import com.eastgate.productservice.exceptions.MissingInputException;
import com.eastgate.productservice.exceptions.NotFoundException;
import com.eastgate.productservice.mapper.ProductMapper;
import com.eastgate.productservice.repository.ProductRepository;
import com.eastgate.productservice.service.ProductService;
import com.eastgate.response.PagingData;
import com.eastgate.response.ResponseDTO;
import com.eastgate.utils.PaginationUtils;
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

import static com.eastgate.utils.Utils.getResponseSuccess;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ResponseDTO createProduct(ProductRequest productRequest) {
        Product entity = productMapper.reqToEntity(productRequest);
        return getResponseSuccess(productMapper.toDto(productRepository.save(entity)),"Successfully!!!");
    }

    public ResponseDTO findProductById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        return getResponseSuccess(productMapper.toDto(productRepository.findById(id)
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

        return getResponseSuccess(productMapper.toDto(productRepository.save(currentProduct)),"Successfully!!!");
    }

    public ResponseDTO deleteProductById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        productRepository.deleteById(id);
        return getResponseSuccess(id,"Successfully!!!");
    }

}
