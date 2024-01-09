package com.sondev.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.APIException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.JwtUtils;
import com.sondev.productservice.dto.request.EvaluateRequest;
import com.sondev.productservice.dto.response.EvaluateDto;
import com.sondev.productservice.dto.response.UserDto;
import com.sondev.productservice.entity.Evaluate;
import com.sondev.productservice.exceptions.NotFoundException;
import com.sondev.productservice.feignclient.UserClient;
import com.sondev.productservice.mapper.EvaluateMapper;
import com.sondev.productservice.repository.EvaluateRepository;
import com.sondev.productservice.repository.ProductRepository;
import com.sondev.productservice.service.EvaluateService;
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
public class EvaluateServiceImpl implements EvaluateService {

    private final EvaluateRepository evaluateRepository;
    private final ProductRepository productRepository;
    private final EvaluateMapper evaluateMapper;
    private final UserClient userClient;
    private final ObjectMapper objectMapper;


    @Override
    public EvaluateDto create(EvaluateRequest evaluateRequest) {
        Evaluate evaluate = Evaluate.builder()
                .content(evaluateRequest.getContent())
                .product(productRepository.findById(evaluateRequest.getProductId()).orElseThrow(
                        () -> new NotFoundException("Can't find product with id" + evaluateRequest.getProductId())))
                .userId(evaluateRequest.getUserId())
                .build();
        return evaluateMapper.toDto(evaluateRepository.save(evaluate));
    }

    @Override
    public PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId) {
        Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Evaluate> evaluatePage = evaluateRepository.findByProductIdAndUserId( pageable,productId,userId);

        Page<EvaluateDto> evaluateDtoPage = evaluatePage.map(evaluateMapper::toDto);

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

        Page<Evaluate> evaluatePage = evaluateRepository.findByProductId( pageable,productId);
//        UserDto user = getUserById(evaluatePage.getContent())

        Page<EvaluateDto> evaluateDtoPage = evaluatePage.map(evaluateMapper::toDto);

        return PagingData.builder()
                .data(evaluateDtoPage.getContent())
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(evaluateDtoPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public EvaluateDto update(String id, EvaluateRequest evaluateRequest, String token) {
        Claims claims = JwtUtils.parseClaims(token);
        String currentUserId = (String) claims.get("userId");

        if (!StringUtils.equals(evaluateRequest.getUserId(), currentUserId)) {
            throw new APIException("You cannot evaluate as another user");
        }

        Evaluate evaluate = evaluateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find evaluate with id" + id));
        evaluate.setContent(evaluateRequest.getContent());

        return evaluateMapper.toDto(evaluateRepository.save(evaluate));
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
