package ru.intervale.course.servlets.enums;

public enum PaymentTrxFields {

    ID, CARD_ID, VALUE, CURRENCY;

    public String getName() {
        return name().toLowerCase();
    }

}
