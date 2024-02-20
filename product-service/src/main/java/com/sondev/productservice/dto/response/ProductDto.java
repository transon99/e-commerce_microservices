package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends AbstractDto<String> {

    private String id;
    private String name;
    private String description;
    private List<ImageDto> imageUrls;
    private BrandDto brandDTO;
    private CategoryDto categoryDTO;
    private Double price;
    private Integer quantity;
    private Integer discount;

}