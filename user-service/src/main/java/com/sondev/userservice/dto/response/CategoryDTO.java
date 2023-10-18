package com.sondev.userservice.dto.response;

import com.sondev.userservice.dto.AbstractDto;
import com.sondev.userservice.entity.Category;
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