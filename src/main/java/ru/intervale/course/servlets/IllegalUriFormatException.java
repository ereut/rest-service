package ru.intervale.course.servlets;

public class IllegalUriFormatException extends Exception {

    public IllegalUriFormatException(){
        super();
    }

    public IllegalUriFormatException(String message) {
        super(message);
    }

    public IllegalUriFormatException(String message, Throwable course) {
        super(message, course);
    }

    public IllegalUriFormatException(Throwable course) {
        super(course);
    }


}
