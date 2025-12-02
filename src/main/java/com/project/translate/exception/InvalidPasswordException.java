package com.project.translate.exception;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {
    private final String errorCode;

    public InvalidPasswordException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
