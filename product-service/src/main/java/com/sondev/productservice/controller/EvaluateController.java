package com.sondev.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.dto.request.EvaluateRequest;
import com.sondev.productservice.dto.response.EvaluateDto;
import com.sondev.productservice.service.EvaluateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/evaluates")
@RestController
@Slf4j
@RequiredArgsConstructor
public class EvaluateController {

    private final EvaluateService evaluateService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@Valid @RequestBody EvaluateRequest evaluateRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create evaluate successful !!",
                evaluateService.create(evaluateRequest)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@PathVariable String id,
                                                  @RequestBody EvaluateRequest evaluateRequest,
                                                  @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update evaluate successful !!",
                evaluateService.update(id, evaluateRequest, token)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getAll(@RequestParam(name = "offset") Integer offset,
                                                  @RequestParam(name = "pageSize") Integer pageSize,
                                                  @RequestParam(name = "user_id", required = false) String userId,
                                                  @RequestParam(name = "product_id") String productId) {
        PagingData evaluatesResponse;

        if (userId == null) {
            evaluatesResponse = evaluateService.getByProduct(offset, pageSize, productId);
        } else {
            evaluatesResponse = evaluateService.getByProductAndUser(offset, pageSize, productId, userId);
        }
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Get evaluates successful !!",
                evaluatesResponse));
    }

}
