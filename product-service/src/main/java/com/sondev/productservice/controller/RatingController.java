package com.sondev.productservice.controller;

import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.dto.request.RatingRequest;
import com.sondev.productservice.service.RatingService;
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

@RequestMapping("/evaluates")
@RestController
@Slf4j
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@Valid @RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Create evaluate successful !!",
                ratingService.create(ratingRequest)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@PathVariable String id,
                                                  @RequestBody RatingRequest ratingRequest,
                                                  @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update evaluate successful !!",
                ratingService.update(id, ratingRequest, token)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getAll(@RequestParam(name = "offset") Integer offset,
                                                  @RequestParam(name = "pageSize") Integer pageSize,
                                                  @RequestParam(name = "user_id", required = false) String userId,
                                                  @RequestParam(name = "product_id") String productId) {
        PagingData evaluatesResponse;

        if (userId == null) {
            evaluatesResponse = ratingService.getByProduct(offset, pageSize, productId);
        } else {
            evaluatesResponse = ratingService.getByProductAndUser(offset, pageSize, productId, userId);
        }
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Get evaluates successful !!",
                evaluatesResponse));
    }

}
