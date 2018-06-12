package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CardJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/card/*")
public class CardServlet extends AbstractServlet<Card, CardJDBCDaoImpl> {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public IDao<Card> getDaoImpl() throws DaoException {
        return DaoFactory.getCardDaoImplFromFactory();
    }
}
