package ru.intervale.course.utils;


import com.google.common.base.Strings;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {

    private static final String SESSION_HEADER_NAME = "X-IV-Authorization";
    private static final String SESSION_HEADER_VALUE_PREFIX = "Session ";
    private static final String RESPONSE_CHARACTER_ENCODING = "UTF-8";
    private static final String RESPONSE_CONTENT_TYPE = "application/json";

    @Nullable
    public static String parseSessionHeader(HttpServletRequest req) {
        String headerValue = req.getHeader(SESSION_HEADER_NAME);
        return Strings.isNullOrEmpty(headerValue) ? null :
                headerValue.replace(SESSION_HEADER_VALUE_PREFIX, "");
    }

   public static PrintWriter getPrintWriter(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(RESPONSE_CHARACTER_ENCODING);
        resp.setContentType(RESPONSE_CONTENT_TYPE);
        return resp.getWriter();
    }

}
