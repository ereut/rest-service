package ru.intervale.course.filters;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Customer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPaymentFilter implements Filter {

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

        Customer customer = (Customer) req.getSession().getAttribute(Constants.CUSTOMER_ATTRIBUTE_NAME);

        if (customer != null) {
            req.getRequestDispatcher("/payment/add").forward(req, resp);
            return;
        } else {

        }




    }

    @Override
    public void destroy() {

    }
}
