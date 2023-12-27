package com.sondev.authservice.mapper;

import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.dto.request.UserRequest;
import com.sondev.authservice.dto.response.AddressDto;
import com.sondev.authservice.dto.response.UserDto;
import com.sondev.authservice.entity.Address;
import com.sondev.authservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User>{
    User reqToEntity(RegisterRequest registerRequest);
    User reqUserToEntity(UserRequest userRequest);

    @Named("mappingAddress")
    default AddressDto mappingAddress(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .fullAddress(address.getFullAddress())
                .city(address.getCity())
                .district(address.getDistrict())
                .build();
    }

    @Mapping(target = "addresses", source = "addresses", qualifiedByName = "mappingAddress")
    UserDto toDto(User user);
}