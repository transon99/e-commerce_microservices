package com.eastgate.serviceregisty.configserver.productservice.service;

import com.eastgate.serviceregisty.configserver.productservice.dto.request.CategoryRequest;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryDTO;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import com.eastgate.serviceregisty.configserver.productservice.exceptions.MissingInputException;
import com.eastgate.serviceregisty.configserver.productservice.exceptions.NotFoundException;
import com.eastgate.serviceregisty.configserver.productservice.mapper.CategoryMapper;
import com.eastgate.serviceregisty.configserver.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository repository;

    private final CategoryMapper categoryMapper;

    public CategoryDTO createCategory(CategoryRequest categoryRequest) {
        Category entity = categoryMapper.reqToEntity(categoryRequest);
        return categoryMapper.toDto(repository.save(entity));
    }

    public CategoryDTO findById(Long id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        return categoryMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id)));
    }

//    public void deleteById(Long id) {
//        repository.deleteById(id);
//    }
//

//    public Page<CategoryResponse> findByCondition(CategoryResponse categoryResponse, Pageable pageable) {
//        Page<Category> entityPage = repository.findAll(pageable);
//        List<Category> entities = entityPage.getContent();
//        return new PageImpl<>(categoryMapper.entityToResponse(entities), pageable, entityPage.getTotalElements());
//    }

    public CategoryDTO update(CategoryRequest categoryRequest, Long id) {
//        CategoryResponse data = findById(id);
//        Category entity = categoryMapper.toEntity(categoryRequest);
//        BeanUtil.copyProperties(data, entity);
//        return save(categoryMapper.toDto(entity));
        return null;
    }

}
