package com.sondev.productservice.mapper;

import com.sondev.productservice.dto.request.BannerRequest;
import com.sondev.productservice.dto.response.BannerDto;
import com.sondev.productservice.entity.Banner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper extends EntityMapper<BannerDto, Banner>{

    Banner reqToEntity(BannerRequest bannerRequest);

}