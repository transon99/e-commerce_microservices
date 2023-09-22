package com.eastgate.serviceregisty.configserver.productservice.dto.response;

import com.eastgate.serviceregisty.configserver.productservice.dto.AbstractDto;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import com.eastgate.serviceregisty.configserver.productservice.entity.Product;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse extends AbstractDto<Long> {
    private Long id;
    private String title;
    private String imageUrl;
    private Set<Category> subCategories;
    private Category parentCategory;
    private Set<Product> products;
}