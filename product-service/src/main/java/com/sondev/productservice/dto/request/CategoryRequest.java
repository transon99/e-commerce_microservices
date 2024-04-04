package com.sondev.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {

    private String name;
    private String parentCatId;
    private MultipartFile imageFile;
    private MultipartFile iconFile;

}