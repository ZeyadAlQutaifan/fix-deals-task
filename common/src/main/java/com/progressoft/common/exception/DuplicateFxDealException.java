package com.progressoft.common.exception;

public class DuplicateFxDealException extends RuntimeException {
    public DuplicateFxDealException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFxDealException(String message) {
        super(message);
    }
}
