package ru.intervale.course.servlets.enums;

public enum CardFields {
    ID, CUSTOMER_ID, PAN, EXPIRY, TITLE;

    public String getName() {
        return name().toLowerCase();
    }
}
