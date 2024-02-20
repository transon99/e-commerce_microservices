package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.productservice.adapter.CloudinaryService;
import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.dto.response.BrandDto;
import com.sondev.productservice.entity.Brand;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Image;
import com.sondev.productservice.mapper.BrandMapper;
import com.sondev.productservice.repository.BrandRepository;
import com.sondev.productservice.service.BrandService;
import com.sondev.productservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageService imageService;


    private final ObjectMapper objectMapper;

    private final BrandMapper brandMapper;

    @Override
    public String create(BrandRequest brandRequest) {

        Brand entity = brandMapper.reqToEntity(brandRequest);

        List<Image> images = brandRequest.getFiles().stream().map(file -> {
            Map result = cloudinaryService.uploadFile(file);
            String imageUrl = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");
            return Image.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
        }).toList();
        entity.setImageUrls(images);

        return brandMapper.toDto(brandRepository.save(entity)).getId();
    }

    @Override
    public PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Brand> brandPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)) {
            brandPage = brandRepository.findAll(pageable);
        } else {
            brandPage = brandRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        return PagingData.builder()
                .data(brandPage)
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(brandPage.getTotalElements())
                .build();
    }

    @Override
    public BrandDto findById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        return brandMapper.toDto(brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id " + id)));
    }

    @Override
    public BrandDto update(BrandRequest brandRequest, String id) {
        Brand currentBrand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id" + id));

        List<Image> imageList;
        if (brandRequest.getFiles() != null) {
            List<Image> currentImageList = currentBrand.getImageUrls();
            currentImageList.forEach(image -> imageService.deleteById(image.getId()));
            imageList = brandRequest.getFiles().stream().map(this::saveImageToCloud).toList();
        } else {
            imageList = currentBrand.getImageUrls();
        }

        Brand newBrand = Brand.builder()
                .id(currentBrand.getId())
                .name(brandRequest.getName())
                .imageUrls(imageList)
                .build();


        return brandMapper.toDto(brandRepository.save(newBrand));
    }

    @Override
    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id " + id));
        brand.getImageUrls().forEach(image ->
                cloudinaryService.deleteFile(image.getPublicId())
        );
        brandRepository.deleteById(id);



        return id;
    }

    @Override
    public List<BrandDto> getAll() {
        return brandMapper.toDto(brandRepository.findAll());
    }

    private Image saveImageToCloud(MultipartFile file) {
        Map result = cloudinaryService.uploadFile(file);
        String imageUrl = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");
        return Image.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
    }

}
