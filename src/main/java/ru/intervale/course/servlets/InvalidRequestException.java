package ru.intervale.course.servlets;

public class InvalidRequestException extends IllegalArgumentException {

    private String message;
    private String parameterName;

    public InvalidRequestException(String message, String parameterName) {
        this.message = message;
        this.parameterName = parameterName;
    }

}
