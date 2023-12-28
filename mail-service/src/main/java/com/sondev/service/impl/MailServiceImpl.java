package com.sondev.service.impl;

import com.sondev.model.MailEvent;
import com.sondev.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    @Override
    public void sendVerificationEmail(MailEvent mailEvent, String url) {
        String subject = "Email Verification";
        String senderName = "SG Shop";

        MimeMessage message = mailSender.createMimeMessage();

        try {
            var messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(from, senderName);
            messageHelper.setTo(mailEvent.getUserDto().getEmail());
            messageHelper.setSubject(subject);
            Map<String, Object> context = Map.of("username", mailEvent.getUserDto().getFirstName() + mailEvent.getUserDto().getLastName(), "verificationLink", url);

            String mailContent = templateEngine.process(
                    "email_templates/verification_template.html", new Context(Locale.getDefault(), context));
            messageHelper.setText(mailContent, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new ApiException(e.getMessage());
        }

        mailSender.send(message);
    }

}
