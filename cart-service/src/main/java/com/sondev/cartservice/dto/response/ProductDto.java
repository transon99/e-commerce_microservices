package com.sondev.cartservice.dto.response;

import com.sondev.cartservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductDto extends AbstractDto<String> {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String sku;
    private Double priceUnit;
    private Integer quantity;
//    private String createdAt;

//    private String lastModifiedAt;
//
//    private String createdBy;
//
//    private String lastModifiedBy;
}