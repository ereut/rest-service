package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.servlets.enums.CustomerFields;
import ru.intervale.course.utils.ServletUtils;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/update")
public class UpdateCustomerServlet extends CustomerServlet {

    @Override
    @Nonnull
    protected Customer parseReqBody(HttpServletRequest req) {

        String sessionId = ServletUtils.parseSessionHeader(req);
        Integer customerId = CustomerSessionMap.getCustomerId(sessionId);

        RequestParameters requestParameters = new RequestParameters(req);
        String password = requestParameters.getOptional(CustomerFields.PASSWORD.getName());
        String name = requestParameters.getOptional(CustomerFields.NAME.getName());
        String surname = requestParameters.getOptional(CustomerFields.SURNAME.getName());
        String telephone = requestParameters.getOptional(CustomerFields.TELEPHONE.getName());
        String address = requestParameters.getOptional(CustomerFields.ADDRESS.getName());
        return new Customer(customerId, null, password, name, surname, telephone, address);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        updateEntity(req, resp);

    }

}
