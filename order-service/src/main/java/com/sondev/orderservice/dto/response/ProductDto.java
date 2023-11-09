package com.sondev.orderservice.dto.response;

import com.sondev.orderservice.dto.AbstractDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends AbstractDto<String> {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String sku;
    private Double priceUnit;
    private Integer quantity;

}