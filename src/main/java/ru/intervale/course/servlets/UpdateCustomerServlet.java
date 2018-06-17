package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.servlets.enums.CustomerFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCustomerServlet extends CustomerServlet {

    @Override
    protected Customer parseReqBody(HttpServletRequest req) {
        RequestParameters requestParameters = new RequestParameters(req);
        Integer id = Integer.parseInt(requestParameters.getRequired(CustomerFields.ID.getName()));
        String password = requestParameters.getOptional(CustomerFields.PASSWORD.getName());
        String name = requestParameters.getOptional(CustomerFields.NAME.getName());
        String surname = requestParameters.getOptional(CustomerFields.SURNAME.getName());
        String telephone = requestParameters.getOptional(CustomerFields.TELEPHONE.getName());
        String address = requestParameters.getOptional(CustomerFields.ADDRESS.getName());
        return new Customer(id, null, password, name, surname, telephone, address);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        updateEntity(req, resp);

    }
}
