package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.servlets.enums.CardFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCardServlet extends CardServlet {

    @Override
    protected Card parseReqBody(HttpServletRequest req) {

        RequestParameters requestParameters = new RequestParameters(req);
        int customerId = Integer.parseInt(requestParameters.getRequired(CardFields.CUSTOMER_ID.getName()));
        String pan = requestParameters.getRequired(CardFields.PAN.getName());
        String expiry = requestParameters.getRequired(CardFields.EXPIRY.getName());
        String title = requestParameters.getOptional(CardFields.TITLE.getName());
        return new Card(customerId, pan, expiry, title);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addEntity(req, resp);

    }

}
