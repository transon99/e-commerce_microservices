package com.sondev.cartservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String sku;
    private Double priceUnit;
    private Integer quantity;
    private String createdAt;

    private String lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;
}