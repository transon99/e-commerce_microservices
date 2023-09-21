package com.eastgate.serviceregisty.configserver.productservice.service;

import com.eastgate.serviceregisty.configserver.productservice.dto.CategoryDto;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import com.eastgate.serviceregisty.configserver.productservice.mapper.CategoryMapper;
import com.eastgate.serviceregisty.configserver.productservice.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository repository, CategoryMapper categoryMapper) {
        this.repository = repository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDto save(CategoryDto categoryDto) {
        Category entity = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CategoryDto findById(Long id) {
        return categoryMapper.toDto(repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new));
    }

    public Page<CategoryDto> findByCondition(CategoryDto categoryDto, Pageable pageable) {
        Page<Category> entityPage = repository.findAll(pageable);
        List<Category> entities = entityPage.getContent();
        return new PageImpl<>(categoryMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CategoryDto update(CategoryDto categoryDto, Long id) {
        CategoryDto data = findById(id);
        Category entity = categoryMapper.toEntity(categoryDto);
        BeanUtil.copyProperties(data, entity);
        return save(categoryMapper.toDto(entity));
    }

}
