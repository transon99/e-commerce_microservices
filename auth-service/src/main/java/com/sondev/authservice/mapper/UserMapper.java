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
        if (address == null) return null;
        return AddressDto.builder()
                .id(address.getId())
                .fullAddress(address.getFullAddress())
                .city(address.getCity())
                .build();
    }

    @Mapping(target = "address", source = "address", qualifiedByName = "mappingAddress")
    UserDto toDto(User user);
}