package ru.intervale.course.servlets;

public class Error {

    public enum Errors {
        SESSION_EXPIRED, NOT_FOUND
    }

    private Errors error;

    public Error() {}

    public Error (Errors error) {
        this.error = error;
    }

}
