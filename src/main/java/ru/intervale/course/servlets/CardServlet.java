package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.CardJDBCDao;
import ru.intervale.course.dao.IDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/card/*")
public class CardServlet extends AbstractServlet<Card, CardJDBCDao> {

    @Override
    protected IDao<Card> getDaoImpl() {
        return new CardJDBCDao(getConnection());
    }

    @Override
    protected Card parseAddReqBody(String body) {
        return null;
    }

    @Override
    protected Card parseUpdateReqBody(String body) {
        return null;
    }


}
