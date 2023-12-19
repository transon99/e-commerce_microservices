package com.sondev.authservice.exceptions;

public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String msg) {
        super(msg);
    }

    public UserNotActivatedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
