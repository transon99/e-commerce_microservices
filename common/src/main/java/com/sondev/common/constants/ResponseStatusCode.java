package com.sondev.common.constants;

public enum ResponseStatusCode {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    UNPROCESSABLE_ENTITY(422),
    INTERNAL_SERVER_ERROR(500),
    FORBIDDEN(403),
    LOCKED(423);

    private int code;

    ResponseStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
