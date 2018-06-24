package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.servlets.enums.CustomerFields;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/add")
public class AddCustomerServlet extends CustomerServlet {

    @Override
    protected Customer parseReqBody(HttpServletRequest req)
            throws InvalidRequestException {

        RequestParameters requestParameters = new RequestParameters(req);
        String login = requestParameters.getRequired(CustomerFields.LOGIN.getName());
        String password = requestParameters.getRequired(CustomerFields.PASSWORD.getName());
        String name = requestParameters.getOptional(CustomerFields.NAME.getName());
        String surname = requestParameters.getOptional(CustomerFields.SURNAME.getName());
        String telephoneNumber = requestParameters.getOptional(CustomerFields.TELEPHONE.getName());
        String address = requestParameters.getOptional(CustomerFields.ADDRESS.getName());
        return new Customer(login, password, name, surname, telephoneNumber, address);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

       addEntity(req, resp);

    }

}
