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
    private String description;
    private String sku;
    private Double priceUnit;
    private Integer quantity;
    private String categoryId;
    private String brandId;

}
