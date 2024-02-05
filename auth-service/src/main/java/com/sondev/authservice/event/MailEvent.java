package com.sondev.authservice.event;

import com.sondev.authservice.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailEvent {
    private UserInfo userInfo;
    private String verificationToken;
}
