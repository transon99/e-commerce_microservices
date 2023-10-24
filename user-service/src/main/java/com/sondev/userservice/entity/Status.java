package com.sondev.userservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    PENDING("PENDING"),
    ACTIVE("ACTIVE");

    private final String status;
}
