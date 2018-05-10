package ru.intervale.course.dao;


public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable course) {
        super(course);
    }

    public DaoException(String message, Throwable course) {
        super(message, course);
    }

}
