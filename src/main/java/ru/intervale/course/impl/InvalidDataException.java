package ru.intervale.course.impl;

public class InvalidDataException extends Exception {

    String message;

    public InvalidDataException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return message;
    }
}
