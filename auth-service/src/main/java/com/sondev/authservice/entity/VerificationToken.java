package com.sondev.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Data
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "confirmation_date")
    private Date confirmationDate;
}
