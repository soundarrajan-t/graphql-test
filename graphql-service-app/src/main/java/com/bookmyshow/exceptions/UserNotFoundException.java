package com.bookmyshow.exceptions;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }

}
