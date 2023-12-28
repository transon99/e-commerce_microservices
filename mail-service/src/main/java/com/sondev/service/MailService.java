package com.sondev.service;

import com.sondev.model.MailEvent;

public interface MailService {

    void sendVerificationEmail(MailEvent mailDto, String url);

}
