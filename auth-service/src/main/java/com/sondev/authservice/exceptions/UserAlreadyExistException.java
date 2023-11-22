package com.sondev.authservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String msg) {
        super(msg);
    }

    public UserAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
