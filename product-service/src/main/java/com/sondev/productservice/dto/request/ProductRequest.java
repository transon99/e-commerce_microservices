package com.sondev.productservice.dto.request;

import com.sondev.productservice.entity.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest{

    private String name;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private List<String> imageUrl;
    @Size(max = 255)
    private String sku;
    private Double priceUnit;
    @Max(Integer.MAX_VALUE)
    private Integer quantity;
    private Category category;

}
