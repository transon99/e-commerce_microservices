package com.sondev.authservice.dto;

import com.sondev.authservice.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailEvent {
    private UserDto userDto;
    private String verificationToken;
}
