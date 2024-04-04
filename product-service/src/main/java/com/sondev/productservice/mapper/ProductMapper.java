package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.ImageDto;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.dto.response.ReviewDto;
import com.sondev.productservice.entity.Image;
import com.sondev.productservice.entity.Product;
import com.sondev.productservice.entity.Review;
import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
    @Named("mappingImageUrls")
    default List<ImageDto> mappingImageUrls(List<Image> imageList) {
        return imageList.stream().map(gallery -> ImageDto.builder()
                .id(gallery.getId())
                .imageUrl(gallery.getImageUrl())
                .build()).toList();
    }

    @Named("mappingReviews")
    default List<ReviewDto> mappingReviews(List<Review> reviews) {
        if (CollectionUtils.isNotEmpty(reviews)){
            return reviews.stream().map(review -> ReviewDto.builder()
                    .id(review.getId())
                    .rate(review.getRate())
                    .userId(review.getUserId())
                    .content(review.getContent())
                    .createDate(review.getCreateDate())
                    .build()).toList();
        }
        return null;
        }


    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    List<ProductDto> toDto(List<Product> productList);

    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    @Mapping(target = "reviews", source = "reviews", qualifiedByName = "mappingReviews")
    ProductDto toDto(Product product);

    Product reqToEntity(ProductRequest productRequest);

}