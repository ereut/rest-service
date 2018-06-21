package ru.intervale.course.filters;

import ru.intervale.course.Constants;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCardFilter implements Filter {

    FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (ServletUtils.parseSessionHeader(req) == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        req.getRequestDispatcher("/card/add").forward(req, resp);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
