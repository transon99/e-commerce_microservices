package com.sondev.userservice.dto.request;

import com.sondev.userservice.entity.Category;
import com.sondev.userservice.entity.Product;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {

    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String imageUrl;
    private Set<Category> subCategories;
    private Category parentCategory;
    private Set<Product> products;
}