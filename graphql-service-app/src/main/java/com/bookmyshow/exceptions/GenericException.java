package com.bookmyshow.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericException implements GraphQLError {

    private final List<Object> path;
    private final List<SourceLocation> location;
    private final ErrorClassification errorType;
    private final String errorCode;
    private final String message;

    public GenericException(String message, List<SourceLocation> location, List<Object> path, ErrorClassification errorType, String errorCode) {
        this.path = path;
        this.location = location;
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return location;
    }

    @Override
    public List<Object> getPath() {
        return path;
    }

    @Override
    public ErrorClassification getErrorType() {
        return errorType;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("errorCode", errorCode);
        return extensions;
    }

}
