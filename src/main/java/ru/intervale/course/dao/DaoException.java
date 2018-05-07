package ru.intervale.course.dao;


public class DaoException extends Exception {

    DaoException() {
        super();
    }

    DaoException(String message) {
        super(message);
    }

    DaoException(Throwable course) {
        super(course);
    }

    DaoException(String message, Throwable course) {
        super(message, course);
    }

}
