package com.sondev.userservice.mapper;

import com.sondev.userservice.dto.request.AddressRequest;
import com.sondev.userservice.dto.response.AddressDto;
import com.sondev.userservice.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDto, Address>{
    Address reqToEntity(AddressRequest addressRequest);
}
