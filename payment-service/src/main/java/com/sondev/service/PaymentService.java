package com.sondev.service;

import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseDTO;
import com.sondev.dto.request.PaymentRequest;
import com.sondev.dto.response.PaymentDto;

import java.util.Map;

public interface PaymentService {

    String createPayment(PaymentRequest paymentRequest);

    PaymentDto findById(String id);

    PagingData getPayments(Integer offset, Integer pageSize, String sortStr);

    PaymentDto updatePayment(Map<String, Object> fields, String id);

    String deleteById(String id);

}
