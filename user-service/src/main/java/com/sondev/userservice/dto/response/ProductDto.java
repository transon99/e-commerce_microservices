package com.sondev.userservice.dto.response;

import com.sondev.userservice.dto.AbstractDto;
import com.sondev.userservice.entity.Category;
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

    @Size(max = 255)
    private String id;
    @Size(max = 255)
    private String title;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private String imageUrl;
    @Size(max = 255)
    private String sku;
    private Double priceUnit;
    @Max(Integer.MAX_VALUE)
    private Integer quantity;
    private Category category;

}