package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CardJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@WebServlet("/card/*")
public class CardServlet extends AbstractEntityServlet<Card, CardJDBCDaoImpl> {

    private enum CardFields {
        ID, CUSTOMERID, PAN, EXPIRY, TITLE
    }


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public IDao<Card> getDaoImpl() throws DaoException {
        return DaoFactory.getCardDaoImplFromFactory();
    }

    @Override
    protected Card parseReqBody(HttpServletRequest req) {

        Card card = new Card();
        Enumeration<String> en = req.getParameterNames();

        while(en.hasMoreElements()) {

            String currentParameter = en.nextElement();
            String currentParameterValue = req.getParameter(currentParameter);
            CardFields field = CardFields.valueOf(currentParameter.toUpperCase());

            switch (field) {
                case ID:
                    card.setId(Integer.valueOf(currentParameterValue));
                    break;
                case CUSTOMERID:
                    card.setCustomerId(Integer.valueOf(currentParameterValue));
                    break;
                case PAN:
                    card.setPanCard(currentParameterValue);
                    break;
                case EXPIRY:
                    card.setExpiryCardDate(currentParameterValue);
                    break;
                case TITLE:
                    card.setTitle(currentParameterValue);
                    break;
            }

        }
        return card;
    }


}
