package ru.intervale.course.servlets.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.intervale.course.servlets.CustomerSessionMap;
import ru.intervale.course.servlets.Error;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class UpdateCurrentSessionFilter implements Filter {

    private FilterConfig filterConfig;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;
        PrintWriter pw = ServletUtils.getPrintWriter(resp);

        String headerValue = ServletUtils.parseSessionHeader(req);

        if (headerValue == null) {
            chain.doFilter(request, response);
            return;
        }

        Integer customerId = CustomerSessionMap.getCustomerId(headerValue);

        if (customerId == null) {
            pw.write(objectMapper.writeValueAsString(new Error(Error.Errors.INVALID_SESSION_ID)));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!CustomerSessionMap.isCurrentSessionValid(customerId)) {
            CustomerSessionMap.invalidateSession(customerId);
            pw.write(objectMapper.writeValueAsString(new Error(Error.Errors.SESSION_EXPIRED)));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CustomerSessionMap.updateSession(customerId);
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

}
