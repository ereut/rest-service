package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.servlets.enums.CardFields;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/card/update")
public class UpdateCardServlet extends CardServlet {

    @Override
    protected Card parseReqBody(HttpServletRequest req) throws InvalidRequestException {

        String sessionId = ServletUtils.parseSessionHeader(req);
        Integer customerId = CustomerSessionMap.getCustomerId(sessionId);

        RequestParameters requestParameters = new RequestParameters(req);
        Integer id = Integer.parseInt(requestParameters.getRequired(CardFields.ID.getName()));
        String pan = requestParameters.getOptional(CardFields.PAN.getName());
        String expiry = requestParameters.getOptional((CardFields.EXPIRY.getName()));
        String title = requestParameters.getOptional(CardFields.TITLE.getName());
        return new Card(id, customerId, pan, expiry, null, title);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        updateEntity(req, resp);

    }
}
