package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CardJDBCDaoImpl;

import javax.servlet.ServletException;

public abstract class CardServlet extends AbstractEntityServlet<Card, CardJDBCDaoImpl> {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected IDao<Card> getDaoImpl() throws DaoException {
        return DaoFactory.getCardDaoImplFromFactory();
    }

}
