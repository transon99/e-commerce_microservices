package com.eastgate.productservice.service.Impl;

import com.eastgate.productservice.exceptions.MissingInputException;
import com.eastgate.productservice.exceptions.NotFoundException;
import com.eastgate.productservice.mapper.CategoryMapper;
import com.eastgate.productservice.repository.CategoryRepository;
import com.eastgate.productservice.dto.request.CategoryRequest;
import com.eastgate.productservice.dto.response.CategoryDTO;
import com.eastgate.productservice.entity.Category;
import com.eastgate.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryDTO createCategory(CategoryRequest categoryRequest) {
        Category entity = categoryMapper.reqToEntity(categoryRequest);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    public List<CategoryDTO> findAllCategories() {
        List<Category> categoryDTOS = categoryRepository.findAll();
        if(CollectionUtils.isEmpty(categoryDTOS)){
            throw new NotFoundException("Can't find any products");
        }
        return categoryMapper.toDto(categoryRepository.findAll());
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
