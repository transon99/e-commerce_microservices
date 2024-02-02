package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import com.sondev.productservice.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends AbstractDto<String> {
    private String id;
    private String name;
    private List<ImageDto> imageUrls;
    private ImageDto iconUrl;
    private Set<ProductDto> products;
    private Category parent;
    private List<Category> children;

}