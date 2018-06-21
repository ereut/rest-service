package ru.intervale.course.utils;


import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String SESSION_HEADER_NAME = "X-IV-Authorization";
    private static final String SESSION_HEADER_VALUE_PREFIX = "Session ";

    public static String parseSessionHeader(HttpServletRequest req) {
        return req.getHeader(SESSION_HEADER_NAME).replace(SESSION_HEADER_VALUE_PREFIX, "");
    }

}
