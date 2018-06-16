package ru.intervale.course.servlets;

import javax.servlet.http.HttpServletRequest;

public class RequestParameters {

    private static final String MISSING_REQUIRED_REQUEST_PARAMETER_MESSAGE =
            "Required request parameter is missing : ";

    HttpServletRequest request;

    public RequestParameters(HttpServletRequest request) {
        this.request = request;
    }

    public String getOptional(String name) {
        return request.getParameter(name);
    }

    public String getRequired(String name) {
        String value = getOptional(name);
        if (value == null) {
            throw new InvalidRequestException(MISSING_REQUIRED_REQUEST_PARAMETER_MESSAGE, name);
        }
        return value;
    }

}
