package ru.intervale.course.servlets;

public class Error {

    public enum Errors {
        SESSION_EXPIRED, NOT_FOUND, UNAUTHORIZED_CUSTOMER, INVALID_SESSION_ID, INVALID_REQUEST_PARAMETERS,
        SERVER_ERROR, BAD_REQUEST
    }

    private Errors error;

    public Error() {}

    public Error (Errors error) {
        this.error = error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    public Errors getError() {
        return error;
    }
}
