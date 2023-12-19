package com.sondev.model;

import lombok.Data;

@Data
public class MailEvent {
    private UserDto userDto;
    private String verificationToken;
}
