package com.sondev.authservice.service;

import com.sondev.authservice.dto.request.ActiveAccountRequest;
import com.sondev.authservice.entity.User;
import com.sondev.authservice.entity.VerificationToken;

public interface VerificationService {
    VerificationToken saveUserVerificationToken(User theUser);
    //    AgoraResponseDto isValidVerificationToken(String token, String userId);
    //
    //    AgoraResponseDto resendActiveRegistration(String userId);
}
