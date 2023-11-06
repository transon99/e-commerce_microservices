package com.sondev.authservice.mapper;

import com.sondev.authservice.dto.request.RegisterRequest;
import com.sondev.authservice.dto.response.UserDto;
import com.sondev.authservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User>{
    User reqToEntity(RegisterRequest registerRequest);
}