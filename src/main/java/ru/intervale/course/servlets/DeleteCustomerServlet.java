package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.servlets.enums.CustomerFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCustomerServlet extends CustomerServlet {

    @Override
    protected Customer parseReqBody(HttpServletRequest req) {
        RequestParameters requestParameters = new RequestParameters(req);
        int id = Integer.parseInt(requestParameters.getRequired(CustomerFields.ID.getName()));
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteEntity(req, resp);
    }

}
