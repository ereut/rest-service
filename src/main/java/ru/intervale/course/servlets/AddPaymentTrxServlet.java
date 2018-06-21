package ru.intervale.course.servlets;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.impl.PaymentTrxJDBCDaoImpl;
import ru.intervale.course.servlets.enums.PaymentTrxFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPaymentTrxServlet extends PaymentTrxServlet {

    private static final String INVALID_CARD_FOR_THIS_CUSTOMER_MESSAGE =
            "This card don'n belongs customer";

    @Override
    protected PaymentTrx parseReqBody(HttpServletRequest req) {

        RequestParameters requestParameters = new RequestParameters(req);
        Customer customer = (Customer) req.getSession().getAttribute(Constants.CUSTOMER_ATTRIBUTE_NAME);
        Integer cardId;
        String pan = null;
        String expiry = null;
        if (customer == null) {
            cardId = null;
            pan = requestParameters.getRequired(PaymentTrxFields.PAN.getName());
            expiry = requestParameters.getRequired(PaymentTrxFields.EXPIRY.getName());
        } else {
                cardId = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.CARD_ID.getName()));
                try {
                    if(!((PaymentTrxJDBCDaoImpl) getDaoImpl()).isCardBelongsCustomer(customer.getId(), cardId)) {
                        throw new IllegalArgumentException(INVALID_CARD_FOR_THIS_CUSTOMER_MESSAGE);
                    }
                } catch (DaoException e) {
                    e.printStackTrace();
                }
        }
        String currency = requestParameters.getRequired(PaymentTrxFields.CURRENCY.getName());
        int value = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.VALUE.getName()));
        return new PaymentTrx(cardId, value, currency, expiry, pan);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addEntity(req, resp);

    }

}
