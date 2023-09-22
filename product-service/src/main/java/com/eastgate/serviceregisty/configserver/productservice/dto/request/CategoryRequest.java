package com.eastgate.serviceregisty.configserver.productservice.dto.request;

import com.eastgate.serviceregisty.configserver.productservice.dto.AbstractDto;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import com.eastgate.serviceregisty.configserver.productservice.entity.Product;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest extends AbstractDto<Long> {

    @Size(max = 255)
    private String title;
    @Size(max = 255)
    private String imageUrl;
    private Set<Category> subCategories;
    private Category parentCategory;
    private Set<Product> products;
}