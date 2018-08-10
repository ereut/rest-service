package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CardJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/card/*")
public class CardServlet extends AbstractEntityServlet<Card, CardJDBCDaoImpl> {

    @Override
    protected Card parseReqBody(HttpServletRequest req) throws InvalidRequestException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected IDao<Card> getDaoImpl() throws DaoException {
        return DaoFactory.getCardDaoImplFromFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        getEntity(req, resp);

    }

}
