package com.bookmyshow.exceptions;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String message) {
        super(message, "USER_WITH_EMAIL_ALREADY_EXISTS");
    }

}