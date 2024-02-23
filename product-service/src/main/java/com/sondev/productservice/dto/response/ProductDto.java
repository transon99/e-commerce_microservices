package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends AbstractDto<String> {

    private String id;
    private String name;
    private String description;
    private List<ImageDto> imageUrls;
    private BrandDto brandDTO;
    private CategoryDto categoryDTO;
    private Double price;
    private Double salePrice;
    private Integer quantity;
    private Double discount;
    private List<ReviewDto> reviews;

}