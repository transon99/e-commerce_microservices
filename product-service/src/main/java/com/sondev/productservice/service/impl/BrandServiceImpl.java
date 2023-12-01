package com.sondev.productservice.service.impl;

import com.sondev.common.response.PagingData;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.dto.response.BrandDTO;
import com.sondev.productservice.entity.Brand;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.exceptions.MissingInputException;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.mapper.BrandMapper;
import com.sondev.productservice.repository.BrandRepository;
import com.sondev.productservice.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;
    

    @Override
    public String create(BrandRequest brandRequest) {
        Brand entity = brandMapper.reqToEntity(brandRequest);
        return brandMapper.toDto(brandRepository.save(entity)).getId();
    }

    @Override
    public PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Brand> brandPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)){
            brandPage = brandRepository.findAll(pageable);
        }else {
            brandPage = brandRepository.findByNameContainingIgnoreCase(searchText,pageable);
        }

        return PagingData.builder()
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(brandPage.getTotalElements())
                .build();
    }

    @Override
    public BrandDTO findById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        return brandMapper.toDto(brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id " + id)));
    }

    @Override
    public BrandDTO update(Map<String, Object> fields, String id) {
        Brand currentBrand = brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find brand with id" + id));

        fields.forEach((key, value) -> {
            // Tìm tên của trường dựa vào "key"
            Field field = ReflectionUtils.findField(Category.class, key);
            if (field == null) throw new NullPointerException("Can't find any field");
            // Set quyền truy cập vào biến kể cả nó là private
            field.setAccessible(true);
            // đặt giá trị cho một field cụ thể trong một đối tượng dựa trên tên của field đó
            ReflectionUtils.setField(field, currentBrand, value);
        });

        return brandMapper.toDto(brandRepository.save(currentBrand));
    }

    @Override
    public String deleteById(String id) {
        if (id == null)
            throw new MissingInputException("Missing input id");
        if (brandRepository.findById(id).isEmpty()) {
            log.error("Unable to delete non-existent data!");
            throw new NotFoundException("Unable to delete non-existent data!");
        }
        brandRepository.deleteById(id);
        return id;
    }

}
