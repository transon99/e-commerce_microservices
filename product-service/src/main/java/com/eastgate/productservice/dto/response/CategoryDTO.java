package com.eastgate.productservice.dto.response;

import com.eastgate.productservice.dto.AbstractDto;
import com.eastgate.productservice.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO extends AbstractDto<Long> {
    private String id;
    private String name;
    private String imageUrl;
    private Set<Category> subCategories;
    private Category parentCategory;
}