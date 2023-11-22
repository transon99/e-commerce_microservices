package com.sondev.authservice.mapper;

import com.sondev.authservice.dto.request.AddressRequest;
import com.sondev.authservice.dto.response.AddressDto;
import com.sondev.authservice.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDto, Address>{
    Address reqToEntity(AddressRequest addressRequest);
}
