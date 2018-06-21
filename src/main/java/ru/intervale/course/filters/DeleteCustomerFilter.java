package ru.intervale.course.filters;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Customer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCustomerFilter implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if ((Customer) req.getSession().getAttribute(Constants.CUSTOMER_ATTRIBUTE_NAME) == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        req.getRequestDispatcher("/customer/delete").forward(req, resp);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
