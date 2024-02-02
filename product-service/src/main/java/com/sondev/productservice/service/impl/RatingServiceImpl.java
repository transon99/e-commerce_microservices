package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.JwtUtils;
import com.sondev.productservice.dto.request.RatingRequest;
import com.sondev.productservice.dto.response.RatingDto;
import com.sondev.productservice.dto.response.UserDto;
import com.sondev.productservice.entity.Rating;
import com.sondev.productservice.feignclient.UserClient;
import com.sondev.productservice.mapper.RatingMapper;
import com.sondev.productservice.repository.EvaluateRepository;
import com.sondev.productservice.repository.ProductRepository;
import com.sondev.productservice.service.RatingService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final EvaluateRepository evaluateRepository;
    private final ProductRepository productRepository;
    private final RatingMapper ratingMapper;
    private final UserClient userClient;
    private final ObjectMapper objectMapper;


    @Override
    public RatingDto create(RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .content(ratingRequest.getContent())
                .product(productRepository.findById(ratingRequest.getProductId()).orElseThrow(
                        () -> new NotFoundException("Can't find product with id" + ratingRequest.getProductId())))
                .userId(ratingRequest.getUserId())
                .build();
        return ratingMapper.toDto(evaluateRepository.save(rating));
    }

    @Override
    public PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId) {
        Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Rating> evaluatePage = evaluateRepository.findByProductIdAndUserId( pageable,productId,userId);

        Page<RatingDto> evaluateDtoPage = evaluatePage.map(ratingMapper::toDto);

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

        Page<Rating> evaluatePage = evaluateRepository.findByProductId( pageable,productId);
//        UserDto user = getUserById(evaluatePage.getContent())

        Page<RatingDto> evaluateDtoPage = evaluatePage.map(ratingMapper::toDto);

        return PagingData.builder()
                .data(evaluateDtoPage.getContent())
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(evaluateDtoPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public RatingDto update(String id, RatingRequest ratingRequest, String token) {
        Claims claims = JwtUtils.parseClaims(token);
        String currentUserId = (String) claims.get("userId");

        if (!StringUtils.equals(ratingRequest.getUserId(), currentUserId)) {
            throw new APIException("You cannot evaluate as another user");
        }

        Rating rating = evaluateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find evaluate with id" + id));
        rating.setContent(ratingRequest.getContent());

        return ratingMapper.toDto(evaluateRepository.save(rating));
    }

    @Override
    public String delete(String id) {
        evaluateRepository.deleteById(id);
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
