package ru.intervale.course.servlets;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;
import ru.intervale.course.servlets.enums.CardFields;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCardServlet extends CardServlet {

    @Override
    protected Card parseReqBody(HttpServletRequest req) throws DaoException {

        String sessionId = ServletUtils.parseSessionHeader(req);
        int customerId;
        CustomerJDBCDaoImpl customerJDBCDao =
                (CustomerJDBCDaoImpl) DaoFactory.getCustomerDaoImplFromFactory();
        customerId = customerJDBCDao.getCustomerIdBySessionId(sessionId);
        RequestParameters requestParameters = new RequestParameters(req);
        String pan = requestParameters.getRequired(CardFields.PAN.getName());
        String expiry = requestParameters.getRequired(CardFields.EXPIRY.getName());
        String title = requestParameters.getOptional(CardFields.TITLE.getName());
        return new Card(customerId, pan, expiry, title);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addEntity(req, resp);

    }

}
