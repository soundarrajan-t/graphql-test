package com.bookmyshow.exceptionhandler;

import com.bookmyshow.exceptions.BaseException;
import com.bookmyshow.exceptions.GenericException;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.validation.ValidationError;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@SuppressWarnings({"unused"})
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

    private static final Map<String, ExceptionErrorCode> ERROR_CODES = new HashMap<String, ExceptionErrorCode>() {
        {
            put(ConstraintViolationException.class.getSimpleName(), ExceptionErrorCode.INVALID_FIELD_VALUE);
            put(ValidationError.class.getSimpleName(), ExceptionErrorCode.QUERY_VALIDATION_ERROR);
            put(IllegalArgumentException.class.getSimpleName(), ExceptionErrorCode.ILLEGAL_ARGUMENT);
            put(AccessDeniedException.class.getSimpleName(), ExceptionErrorCode.ACCESS_DENIED);
        }
    };

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> list) {
        return list.stream().map(this::getNested).collect(Collectors.toList());
    }

    private GraphQLError getNested(GraphQLError error) {
        String errorCode;
        String errorMessage;
        if (error instanceof ExceptionWhileDataFetching) {
            Throwable exception = ((ExceptionWhileDataFetching) error).getException();
            errorCode = isBaseException(exception)
                    ? ((BaseException) exception).getErrorCode()
                    : getExceptionErrorCode(exception).toString();

            errorMessage = isBaseException(exception)
                    ? exception.getMessage()
                    : getErrorMessage(exception);
        } else {
            errorCode = getExceptionErrorCode(error).toString();
            errorMessage = getErrorMessage(error);
        }
        return new GenericException(errorMessage, error.getLocations(), error.getPath(), error.getErrorType(), errorCode);
    }

    private String getErrorMessage(Throwable exception) {
        return getExceptionErrorCode(exception).formatErrorMessage(exception.getMessage());
    }

    private String getErrorMessage(GraphQLError error) {
        return getExceptionErrorCode(error).formatErrorMessage(error.getMessage());
    }

    private boolean isBaseException(Throwable exception) {
        return exception instanceof BaseException;
    }

    private ExceptionErrorCode getExceptionErrorCode(GraphQLError error) {
        return ERROR_CODES.get(error.getClass().getSimpleName());
    }

    private ExceptionErrorCode getExceptionErrorCode(Throwable exception) {
        return ERROR_CODES.get(exception.getClass().getSimpleName());
    }

}
