package com.sondev.authservice.service.impl;

import com.sondev.authservice.dto.request.ActiveAccountRequest;
import com.sondev.authservice.entity.User;
import com.sondev.authservice.entity.VerificationToken;
import com.sondev.authservice.repository.VerificationTokenRepository;
import com.sondev.authservice.service.VerificationService;
import com.sondev.common.exceptions.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationTokenRepository tokenRepository;

    @Override
    @Transactional
    public VerificationToken saveUserVerificationToken(User theUser) {
        var verificationToken = new VerificationToken();
        verificationToken.setUserId(theUser.getId());
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setCreatedDate(Calendar.getInstance().getTime());

        return tokenRepository.save(verificationToken);
    }

}
