package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/customer/*")
public class GetCustomerServlet extends CustomerServlet {

    @Override
    protected Customer parseReqBody(HttpServletRequest req) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        getEntity(req, resp);

    }
}
