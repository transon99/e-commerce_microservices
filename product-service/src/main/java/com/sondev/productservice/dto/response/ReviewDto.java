package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewDto extends AbstractDto<String> {

    private String id;

    private Double rate;

    private String content;

    private String userId;

    private ProductDto product;

    private Date createDate;
}
