package com.sondev.mailservice.model;

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
