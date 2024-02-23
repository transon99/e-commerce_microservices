package com.sondev.mailservice.service.impl;


import com.sondev.mailservice.model.MailEvent;
import com.sondev.mailservice.service.MailService;
import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

    @Value("${client.baseUrl}")
    private String clientUrl;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    @Override
    @KafkaListener(topics = "verification-mail")
    public void sendVerificationEmail(MailEvent mailEvent) {
        String subject = "Email Verification";
        String senderName = "SG Shop";

        MimeMessage message = mailSender.createMimeMessage();
        log.info("Got message <{}>", mailEvent.getUserInfo().getId());

        String url = String.format("%s/verification/%s", clientUrl, mailEvent.getVerificationToken());

        log.info("Got url <{}>", url);

        try {
            var messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(from, senderName);
            messageHelper.setTo(mailEvent.getUserInfo().getEmail());
            messageHelper.setSubject(subject);
            Map<String, Object> context = Map.of("username", mailEvent.getUserInfo().getFirstName() + mailEvent.getUserInfo().getLastName(), "verificationLink", url);

            String mailContent = templateEngine.process(
                    "verification_template", new Context(Locale.getDefault(), context));
            messageHelper.setText(mailContent, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new ApiException(e.getMessage());
        }

        mailSender.send(message);
    }

}
