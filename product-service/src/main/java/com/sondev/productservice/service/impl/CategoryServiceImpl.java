package com.sondev.productservice.service.impl;

import com.sondev.common.response.PagingData;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.dto.response.CategoryDTO;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.exceptions.MissingInputException;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.mapper.CategoryMapper;
import com.sondev.productservice.repository.CategoryRepository;
import com.sondev.productservice.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public String createCategory(CategoryRequest categoryRequest) {
        Category entity = categoryMapper.reqToEntity(categoryRequest);
        return categoryMapper.toDto(categoryRepository.save(entity)).getId();
    }

    public PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Category> productPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)){
            productPage = categoryRepository.findAll(pageable);
        }else {
            productPage = categoryRepository.findByNameContainingIgnoreCase(searchText,pageable);
        }

        return PagingData.builder()
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(productPage.getTotalElements())
                .build();
    }

    public CategoryDTO findCategoryById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id)));
    }

    public String deleteCategoryById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");

        categoryRepository.deleteById(id);
        return id;
    }

    public CategoryDTO updateCategory(Map<String, Object> fields, String id) {
        Category currentCategory = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find category with id" + id));

        fields.forEach((key, value) -> {
            // Tìm tên của trường dựa vào "key"
            Field field = ReflectionUtils.findField(Category.class, key);
            if (field == null) throw new NullPointerException("Can't find any field");
            // Set quyền truy cập vào biến kể cả nó là private
            field.setAccessible(true);
            // đặt giá trị cho một field cụ thể trong một đối tượng dựa trên tên của field đó
            ReflectionUtils.setField(field, currentCategory, value);
        });

        return categoryMapper.toDto(categoryRepository.save(currentCategory));
    }

}
