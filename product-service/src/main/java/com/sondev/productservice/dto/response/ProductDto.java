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
    private String title;
    private String description;
    private List<GalleryDTO> imageUrls;
    private BrandDTO brandDTO;
    private CategoryDTO categoryDTO;
    private String sku;
    private Double priceUnit;
    private Integer quantity;

}