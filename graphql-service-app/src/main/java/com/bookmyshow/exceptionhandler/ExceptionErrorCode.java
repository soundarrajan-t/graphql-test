package com.bookmyshow.exceptionhandler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ExceptionErrorCode {

    INVALID_FIELD_VALUE {
        @Override
        public String formatErrorMessage(String message) {
            String[] violationErrors = message.split(",");
            return Stream.of(violationErrors)
                    .map(this::stripMessage)
                    .collect(Collectors.joining(","));
        }

        private String stripMessage(String error) {
            String[] errorMessage = error.split(":");
            return errorMessage[errorMessage.length - 1];
        }
    },
    ILLEGAL_ARGUMENT {
        @Override
        public String formatErrorMessage(String message) {
            StringBuilder modifiedMessage = new StringBuilder();
            int openBracketIndexFromLast = message.lastIndexOf("[") + 2;
            int closeBracketIndexFromLast = message.lastIndexOf("]") - 1;
            modifiedMessage.append(message, openBracketIndexFromLast, closeBracketIndexFromLast).append(": ");
            int enumClassIndex = message.lastIndexOf("Enum class: ") + 12;
            while (message.charAt(enumClassIndex - 1) != ']') {
                modifiedMessage.append(message.charAt(enumClassIndex));
                enumClassIndex++;
            }
            return modifiedMessage.append(" any one of these Required").toString();
        }
    },
    QUERY_VALIDATION_ERROR {
        @Override
        public String formatErrorMessage(String message) {
            StringBuilder modifiedMessage = new StringBuilder();
            String[] validationErrors = message.split(" ");
            for (int error = 0; error < 7; error++) {
                modifiedMessage.append(validationErrors[error]).append(" ");
            }
            return modifiedMessage.toString();
        }
    },
    ACCESS_DENIED {
        @Override
        public String formatErrorMessage(String message) {
            return message + " You don't have permission to do this operation";
        }
    };

    public abstract String formatErrorMessage(String message);

}