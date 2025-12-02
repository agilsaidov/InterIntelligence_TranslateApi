package com.project.translate.exception;

public class DeletedUserException extends RuntimeException {
    public DeletedUserException(String message) {
        super(message);
    }
}
