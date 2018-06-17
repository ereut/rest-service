package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.servlets.enums.PaymentTrxFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPaymentTrxServlet extends PaymentTrxServlet {

    @Override
    protected PaymentTrx parseReqBody(HttpServletRequest req) {
        RequestParameters requestParameters = new RequestParameters(req);
        Integer cardId = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.CARD_ID.getName()));
        String currency = requestParameters.getRequired(PaymentTrxFields.CURRENCY.getName());
        int value = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.VALUE.getName()));
        return new PaymentTrx(cardId, value, currency);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addEntity(req, resp);

    }

}
