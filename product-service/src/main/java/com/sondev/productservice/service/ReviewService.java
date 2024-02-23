package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.ReviewRequest;
import com.sondev.productservice.dto.response.ReviewDto;

public interface ReviewService {

    ReviewDto create(ReviewRequest reviewRequest);

    PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId);

    PagingData getByProduct(Integer offset, Integer pageSize, String productId);

    ReviewDto update(String id, ReviewRequest reviewRequest, String token);

    String delete(String id);

}
