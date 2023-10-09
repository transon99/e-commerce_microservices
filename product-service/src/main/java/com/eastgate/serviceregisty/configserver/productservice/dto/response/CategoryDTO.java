package com.eastgate.serviceregisty.configserver.productservice.dto.response;

import com.eastgate.serviceregisty.configserver.productservice.dto.AbstractDto;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import com.eastgate.serviceregisty.configserver.productservice.entity.Product;
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
    private String title;
    private String imageUrl;
    private Set<Category> subCategories;
    private Category parentCategory;
}