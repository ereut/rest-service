package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.servlets.enums.CardFields;
import ru.intervale.course.utils.ServletUtils;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/card/delete")
public class DeleteCardServlet extends CardServlet {

    @Override
    @Nonnull
    protected Card parseReqBody(HttpServletRequest req) throws InvalidRequestException {

        String sessionId = ServletUtils.parseSessionHeader(req);
        Integer customerId = CustomerSessionMap.getCustomerId(sessionId);

        RequestParameters requestParameters = new RequestParameters(req);
        Integer id = Integer.parseInt(requestParameters.getRequired(CardFields.ID.getName()));
        Card card = new Card();
        card.setCustomerId(customerId);
        card.setId(id);
        return card;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        deleteEntity(req, resp);

    }
}
