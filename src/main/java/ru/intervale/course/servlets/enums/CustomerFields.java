package ru.intervale.course.servlets.enums;

public enum CustomerFields {
    ID, LOGIN, PASSWORD, NAME, SURNAME, TELEPHONE, ADDRESS;

    public String getName() {
        return name().toLowerCase();
    }
}
