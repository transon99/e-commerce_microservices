package com.sondev.productservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductSearchRequest {
    @Size(max = 500)
    private String searchText;
    private Integer offset;
    private Integer pageSize;
    private String sort;
}
