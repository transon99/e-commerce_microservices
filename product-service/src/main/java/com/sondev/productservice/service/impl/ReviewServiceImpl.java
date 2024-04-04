package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.JwtUtils;
import com.sondev.productservice.dto.request.ReviewRequest;
import com.sondev.productservice.dto.response.ReviewDto;
import com.sondev.productservice.dto.response.UserDto;
import com.sondev.productservice.entity.Review;
import com.sondev.productservice.feignclient.UserClient;
import com.sondev.productservice.mapper.ReviewMapper;
import com.sondev.productservice.repository.ReviewRepository;
import com.sondev.productservice.repository.ProductRepository;
import com.sondev.productservice.service.ReviewService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;
    private final UserClient userClient;
    private final ObjectMapper objectMapper;


    @Override
    public ReviewDto create(ReviewRequest reviewRequest) {
        Review review = Review.builder()
                .rate(reviewRequest.getRate())
                .content(reviewRequest.getContent())
                .product(productRepository.findById(reviewRequest.getProductId()).orElseThrow(
                        () -> new NotFoundException("Can't find product with id" + reviewRequest.getProductId())))
                .userId(reviewRequest.getUserId())
                .createDate(new Date())
                .build();
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId) {
        Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Review> evaluatePage = reviewRepository.findByProductIdAndUserId( pageable,productId,userId);

        Page<ReviewDto> evaluateDtoPage = evaluatePage.map(reviewMapper::toDto);

        return PagingData.builder()
                .data(evaluateDtoPage)
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(evaluateDtoPage.getTotalElements())
                .build();
    }

    @Override
    public PagingData getByProduct(Integer offset, Integer pageSize, String productId) {
        Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Review> evaluatePage = reviewRepository.findByProductId( pageable,productId);
//        UserDto user = getUserById(evaluatePage.getContent())

        Page<ReviewDto> evaluateDtoPage = evaluatePage.map(reviewMapper::toDto);

        return PagingData.builder()
                .data(evaluateDtoPage.getContent())
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(evaluateDtoPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public ReviewDto update(String id, ReviewRequest reviewRequest, String token) {
        Claims claims = JwtUtils.parseClaims(token);
        String currentUserId = (String) claims.get("userId");

        if (!StringUtils.equals(reviewRequest.getUserId(), currentUserId)) {
            throw new APIException("You cannot evaluate as another user");
        }

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find evaluate with id" + id));
        review.setContent(reviewRequest.getContent());

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public String delete(String id) {
        reviewRepository.deleteById(id);
        return id;
    }

    private UserDto getUserById(String id, String token){
        ResponseMessage userResponse = userClient.findById(token,id).getBody();
        assert userResponse != null;
        if (userResponse.getData() != null) {
            return objectMapper.convertValue(userResponse.getData(), UserDto.class);

        }
        return null;
    }

}
