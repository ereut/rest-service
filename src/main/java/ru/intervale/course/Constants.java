package ru.intervale.course;

import java.text.SimpleDateFormat;

public class Constants {

    public static final String CUSTOMERS_PRINT_HEADER = "All registed customers";
    public static final String CARDS_PRINT_HEADER = "Cards of all customers";
    public static final String PAYMENTS_PRINT_HEADER = "All payments";
    public static final String DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";
    public static final String EXPIRY_DATE_PATTERN = "MMyy";
    public static final SimpleDateFormat CARD_EXPIRY_DATE_FORMAT =
            new SimpleDateFormat(EXPIRY_DATE_PATTERN);
    public static final String ILLEGAL_EXPIRY_CARD_DATE_MESSAGE = "Illegal expiry card date ";
    public static final String URI_DELIMITER = "/";
    public static final String CUSTOMER_ATTRIBUTE_NAME = "customer";











}
