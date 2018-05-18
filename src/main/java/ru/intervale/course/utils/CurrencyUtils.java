package ru.intervale.course.utils;

public class CurrencyUtils {

    public static String getStringCurrencyValue(int value) {
        int i = value % 100;
        return value / 100 + "." + ((i < 10) ? "0" : "") + i;
    }

}
