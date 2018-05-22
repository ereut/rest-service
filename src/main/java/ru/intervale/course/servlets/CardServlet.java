package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.CardJDBCDao;
import ru.intervale.course.dao.IDao;

import javax.servlet.annotation.WebServlet;

@WebServlet("/card/*")
public class CardServlet extends AbstractServlet<Card, CardJDBCDao> {

    @Override
    protected IDao<Card> getDaoImpl() {
        return new CardJDBCDao(getConnection());
    }

}
