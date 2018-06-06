package ru.intervale.course.servlets;

import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.CardJDBCDao;
import ru.intervale.course.dao.IDao;

import javax.servlet.annotation.WebServlet;

@WebServlet("/card/*")
public class CardServlet extends AbstractServlet<Card, CardJDBCDao> {

    private enum CardFields {
        ID, CUSTOMERID, PAN, EXPIRY
    }

    @Override
    protected IDao<Card> getDaoImpl() {
        return new CardJDBCDao(getConnection());
    }

    @Override
    protected Card parseReqBody(String body) {

        Card card = new Card();

        for (String str : body.split("\n")) {

            CardFields field = CardFields.valueOf(getKeyFromLine(body).toUpperCase());

            switch (field) {
                case ID:
                    card.setId(Integer.valueOf(getValueFromLine(str).trim()));
                    break;
                case CUSTOMERID:
                    card.setCustomerId(Integer.valueOf(getValueFromLine(str).trim()));
                    break;
                case PAN:
                    card.setPanCard(getValueFromLine(str));
                    break;
                case EXPIRY:
                    card.setExpiryCardDate(getValueFromLine(getValueFromLine(str)));
                    break;
            }
        }
        return card;
    }

}
