package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RatingDto extends AbstractDto<String> {

    private String content;

//    private String userId;
//
//    private String productId;

}
