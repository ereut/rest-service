package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.servlets.enums.CardFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCardServlet extends CardServlet {

    @Override
    protected Card parseReqBody(HttpServletRequest req) {
        RequestParameters requestParameters = new RequestParameters(req);
        int id = Integer.parseInt(requestParameters.getRequired(CardFields.ID.getName()));
        Card card = new Card();
        card.setId(id);
        return card;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        deleteEntity(req, resp);

    }
}
