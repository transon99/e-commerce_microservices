package com.sondev.dto.response;

import com.sondev.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends AbstractDto<String> {

    private String id;
    private String name;
    private String description;
    private String sku;
    private Double priceUnit;
    private Integer quantity;
    private Integer discount;

}