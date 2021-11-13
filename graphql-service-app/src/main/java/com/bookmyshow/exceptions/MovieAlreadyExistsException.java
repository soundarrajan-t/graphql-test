package com.bookmyshow.exceptions;

public class MovieAlreadyExistsException extends BaseException {

    public MovieAlreadyExistsException(String message) {
        super(message, "MOVIE_WITH_NAME_ALREADY_EXISTS");
    }

}
