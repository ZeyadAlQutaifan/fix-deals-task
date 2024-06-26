package com.progressoft.common.exception;

public class DuplicateFixDealException extends RuntimeException {
    public DuplicateFixDealException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFixDealException(String message) {
        super(message);
    }
}
