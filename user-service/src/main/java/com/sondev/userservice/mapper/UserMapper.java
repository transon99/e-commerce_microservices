package com.sondev.userservice.mapper;

import com.sondev.userservice.dto.request.AddressRequest;
import com.sondev.userservice.dto.request.RegisterRequest;
import com.sondev.userservice.dto.response.UserDto;
import com.sondev.userservice.entity.Address;
import com.sondev.userservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User>{
    User reqToEntity(RegisterRequest registerRequest);
}