package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetCardServlet extends CardServlet {

    @Override
    protected Card parseReqBody(HttpServletRequest req) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        getEntity(req, resp);

    }

}
