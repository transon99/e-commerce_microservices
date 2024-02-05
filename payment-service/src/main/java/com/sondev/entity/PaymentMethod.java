package com.sondev.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    COD("COD"),
    STRIPE("STRIPE"),
    VN_PAY("VN_PAY");

    private final String method;
}
