package com.sondev.productservice.dto.response;

import com.sondev.productservice.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto extends AbstractDto<String> {
    private String id;
    private String thumbnailUrl;
}