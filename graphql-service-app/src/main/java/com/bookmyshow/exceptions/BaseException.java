package com.bookmyshow.exceptions;

public class BaseException extends RuntimeException {

    private final String errorCode;
    private final String message;

    public BaseException(String message, String errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}