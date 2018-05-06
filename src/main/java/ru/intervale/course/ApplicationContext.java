package ru.intervale.course;

import java.util.ResourceBundle;

public class ApplicationContext {

    private static final ResourceBundle RESOURSE_BUNDLE =
            ResourceBundle.getBundle("strings");

    public static String getPropertyValue (String key) {
        return RESOURSE_BUNDLE.getString(key);
    }

}
