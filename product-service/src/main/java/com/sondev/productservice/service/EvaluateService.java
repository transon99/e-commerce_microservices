package com.sondev.productservice.service;

import com.sondev.common.response.PagingData;
import com.sondev.productservice.dto.request.EvaluateRequest;
import com.sondev.productservice.dto.response.EvaluateDto;

public interface EvaluateService {

    EvaluateDto create(EvaluateRequest evaluateRequest);

    PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId);

    PagingData getByProduct(Integer offset, Integer pageSize, String productId);

    EvaluateDto update(String id, EvaluateRequest evaluateRequest, String token);

    String delete(String id);

}
