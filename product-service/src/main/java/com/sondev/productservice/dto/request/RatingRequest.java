package com.sondev.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RatingRequest {

    private String content;

    private String userId;

    private String productId;

}
