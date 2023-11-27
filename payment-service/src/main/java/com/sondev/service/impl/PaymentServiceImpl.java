package com.sondev.service.impl;

import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.dto.request.PaymentRequest;
import com.sondev.dto.response.PaymentDto;
import com.sondev.entity.Payment;
import com.sondev.mapper.PaymentMapper;

import com.sondev.repository.PaymentRepository;
import com.sondev.service.PaymentService;
import jakarta.transaction.Transactional;
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
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public String createPayment(PaymentRequest paymentRequest) {
        Payment entity = paymentMapper.reqToEntity(paymentRequest);
        return paymentMapper.toDto(paymentRepository.save(entity)).getId();
    }

    public PaymentDto findById(String paymentId) {
        if (paymentId == null) {
            throw new MissingInputException("Missing input paymentId");
        }
        PaymentDto paymentDto = paymentMapper.toDto(paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + paymentId)));

        log.info("PaymentServiceImpl | findById | PaymentDto :" + paymentDto.toString());

        return paymentDto;
    }

    public PagingData getPayments(Integer offset, Integer pageSize, String sortStr) {

        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        Page<Payment> paymentPage = paymentRepository.findAll(pageable);

        return PagingData.builder()
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(paymentPage.getTotalElements())
                .build();
    }

    public PaymentDto updatePayment(Map<String, Object> fields, String paymentId) {

        Payment currentPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Can't find payment with id" + paymentId));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Payment.class, key);
            if (field == null) {
                throw new NullPointerException("Can't find any field");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, currentPayment, value);
        });

        return paymentMapper.toDto(paymentRepository.save(currentPayment));
    }

    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        if (paymentRepository.findById(id).isEmpty()) {
            log.error("Unable to delete non-existent data!");
            throw new NotFoundException("Unable to delete non-existent data!");
        }
        paymentRepository.deleteById(id);
        return id;
    }

}
