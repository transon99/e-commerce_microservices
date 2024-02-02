package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.RatingRequest;
import com.sondev.productservice.dto.response.RatingDto;

public interface RatingService {

    RatingDto create(RatingRequest ratingRequest);

    PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId);

    PagingData getByProduct(Integer offset, Integer pageSize, String productId);

    RatingDto update(String id, RatingRequest ratingRequest, String token);

    String delete(String id);

}
