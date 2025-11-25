package com.project.translate.exception;

import lombok.Getter;

@Getter
public class StarredTranslationProcessException extends RuntimeException {
    private final String errorCode;

    public StarredTranslationProcessException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
