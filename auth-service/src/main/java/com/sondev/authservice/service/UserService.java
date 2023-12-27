package com.sondev.authservice.service;

import com.sondev.authservice.dto.request.ActiveAccountRequest;
import com.sondev.authservice.dto.request.UserRequest;
import com.sondev.authservice.dto.response.UserDto;
import com.sondev.authservice.entity.User;
import com.sondev.common.response.PagingData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(UserRequest userRequest);

    UserDto getCurrentUser(String token);

    List<UserDto> getAllUser();

    PagingData getUsers(String searchText, Integer offset, Integer pageSize, String sortStr);

    String updateAvatar(MultipartFile file, String id);

    UserDto getUserById(String id);

    UserDto updateUser(Map<String, Object> fields, String id);

    String deleteUser(String id);

    String activeUser(ActiveAccountRequest request);

}
