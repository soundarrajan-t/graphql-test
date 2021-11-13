package com.bookmyshow.exceptions;

public class MovieNotAvailableException extends BaseException {

    public MovieNotAvailableException(String message) {
        super(message, "CAN_NOT_FETCH_MOVIE_BY_NAME");
    }

}