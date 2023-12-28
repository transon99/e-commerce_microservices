package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import com.sondev.productservice.entity.Category;
import com.sondev.productservice.entity.Gallery;
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
public class CategoryDTO extends AbstractDto<String> {
    private String id;
    private String name;
    private List<GalleryDTO> imageUrls;
    private GalleryDTO iconUrl;
    private Set<ProductDto> products;
    private Category parent;
    private List<Category> children;

}