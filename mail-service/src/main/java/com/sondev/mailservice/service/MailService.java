package com.sondev.mailservice.service;


import com.sondev.mailservice.model.MailEvent;

public interface MailService {

    void sendVerificationEmail(MailEvent mailDto);

}
