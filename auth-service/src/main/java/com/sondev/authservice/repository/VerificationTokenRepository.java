package com.sondev.authservice.repository;

import com.sondev.authservice.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByTokenAndUserId(String token, String userId);
    Optional<VerificationToken> findByToken(String token);
}
