package ru.intervale.course.servlets.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.intervale.course.servlets.Error;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {
        "/customer/delete",
        "/customer/update",
        "/session/finish",
        "/card/add",
        "/card/update",
        "/card/delete",
})

public class UnauthorizedCustomerFilter implements Filter {

    private FilterConfig config;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        PrintWriter pw = ServletUtils.getPrintWriter(resp);

        if (ServletUtils.parseSessionHeader(req) == null) {
            pw.write(objectMapper.writeValueAsString(new Error(Error.Errors.UNAUTHORIZED_CUSTOMER)));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

}
