package com.sondev.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest{

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Double discount;
    private String categoryId;
    private String brandId;
    List<MultipartFile> files;

}
