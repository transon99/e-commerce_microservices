package com.eastgate.serviceregisty.configserver.productservice.controller;

import com.eastgate.serviceregisty.configserver.productservice.dto.CategoryDto;

import java.util.Collections;
import java.util.List;

public class CategoryBuilder {

    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static CategoryDto getDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId("1");
        return dto;
    }

}
