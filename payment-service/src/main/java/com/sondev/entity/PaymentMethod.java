package com.sondev.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    COD("COD"),
    MOMO("Momo"),
    VN_PAY("Vn_Pay");

    private final String method;
}
