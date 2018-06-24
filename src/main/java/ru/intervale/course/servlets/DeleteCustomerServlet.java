package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/delete")
public class DeleteCustomerServlet extends CustomerServlet {

    @Override
    protected Customer parseReqBody(HttpServletRequest req) {
        String sessionId = ServletUtils.parseSessionHeader(req);
        Integer customerId = CustomerSessionMap.getCustomerId(sessionId);
        Customer customer = new Customer();
        customer.setId(customerId);
        CustomerSessionMap.invalidateSession(customerId);
        return customer;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        deleteEntity(req, resp);

    }

}
