package ru.intervale.course.servlets;

public class InvalidRequestException extends Exception {

    private String message;
    private String parameterName;

    public InvalidRequestException(String message, String parameterName) {
        this.message = message;
        this.parameterName = parameterName;
    }

    @Override
    public String toString() {
        return message + parameterName;
    }
}
