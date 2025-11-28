package com.project.translate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

    public UserException(HttpStatus status ,String errorCode,String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
