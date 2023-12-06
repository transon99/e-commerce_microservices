package com.sondev.productservice.service.impl;

import com.sondev.common.constants.ResponseStatusCode;
import com.sondev.common.exceptions.APIException;
import com.sondev.productservice.dto.request.GalleryRequest;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Gallery;
import com.sondev.productservice.entity.Product;
import com.sondev.productservice.exceptions.MissingInputException;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.mapper.BrandMapper;
import com.sondev.productservice.mapper.CategoryMapper;
import com.sondev.productservice.mapper.ProductMapper;
import com.sondev.productservice.repository.BrandRepository;
import com.sondev.productservice.repository.CategoryRepository;
import com.sondev.productservice.repository.GalleryRepository;
import com.sondev.productservice.repository.ProductRepository;
import com.sondev.productservice.service.CategoryService;
import com.sondev.productservice.service.GalleryService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;

    public String createProduct(ProductRequest productRequest) {
        log.info("ProductServiceImpl | createProduct is called");
        Product entity = productMapper.reqToEntity(productRequest);

        List<Gallery> galleryList = productRequest.getImageUrls().stream().map(imageUrl -> {
            Gallery gallery = new Gallery();
            gallery.setThumbnailUrl(imageUrl);
            //            gallery.setProduct(entity);
            return gallery;
        }).toList();
        entity.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Can't find category with id" + productRequest.getCategoryId())));
        entity.setBrand(brandRepository.findById(productRequest.getBrandId()).orElseThrow(
                () -> new NotFoundException("Can't find brand with id" + productRequest.getBrandId())));
        entity.setThumbnailUrls(galleryList);
        return productMapper.toDto(productRepository.save(entity)).getId();
    }

    public ProductDto findProductById(String productId) {
        log.info("ProductServiceImpl | findProductById is called");
        log.info("ProductServiceImpl | findProductById | Get the product for productId: {}", productId);
        if (productId == null) {
            throw new MissingInputException("Missing input productId");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + productId));

        ProductDto productDto = productMapper.toDto(product);

        productDto.setBrandDTO(brandMapper.toDto(product.getBrand()));
        productDto.setCategoryDTO(categoryMapper.toDto(product.getCategory()));

        log.info("ProductServiceImpl | findProductById | productDto :" + productDto.toString());

        return productDto;
    }

    public PagingData getProducts(String searchText, Integer offset, Integer pageSize, String sortStr) {
        log.info("ProductServiceImpl | findProductById is called");
        log.info("ProductServiceImpl | findProductById | offset {}, pageSize {}, sortStr {}   : ", offset, pageSize,
                sortStr);

        Page<Product> productPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        return PagingData.builder()
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(productPage.getTotalElements())
                .build();
    }

    public ProductDto updateProduct(Map<String, Object> fields, String productId) {
        log.info("ProductServiceImpl | updateProduct is called");
        log.info("ProductServiceImpl | updateProduct | Update the product for productId: {}", productId);

        Product currentProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Can't find product with id" + productId));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Category.class, key);
            if (field == null) {
                throw new NullPointerException("Can't find any field");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, currentProduct, value);
        });

        return productMapper.toDto(productRepository.save(currentProduct));
    }

    public String deleteProductById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        productRepository.deleteById(id);
        return id;
    }

    @Override
    public void reduceQuantity(String productId, Integer quantity) {
        log.info("Reduce Quantity {} for Id: {}", quantity, productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "Can't find product with id" + productId
                ));

        if (product.getQuantity() < quantity) {
            throw new APIException(
                    "Product does not have sufficient Quantity"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");
    }

}
