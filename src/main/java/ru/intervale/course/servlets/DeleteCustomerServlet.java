package ru.intervale.course.servlets;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCustomerServlet extends CustomerServlet {

    @Override
    protected Customer parseReqBody(HttpServletRequest req) {

        Customer customer = (Customer) req.getSession().getAttribute(Constants.CUSTOMER_ATTRIBUTE_NAME);
        return customer;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        deleteEntity(req, resp);

    }

}
