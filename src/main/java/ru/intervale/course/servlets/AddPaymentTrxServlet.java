package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.servlets.enums.PaymentTrxFields;
import ru.intervale.course.utils.ServletUtils;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment/add")
public class AddPaymentTrxServlet extends PaymentTrxServlet {

    @Override
    @Nonnull
    protected PaymentTrx parseReqBody(HttpServletRequest req) throws InvalidRequestException{

        RequestParameters requestParameters = new RequestParameters(req);

        Integer cardId;
        String pan = null;
        String expiry = null;
        Integer customerId = null;
        cardId = null;
        String sessionId = ServletUtils.parseSessionHeader(req);

        if (sessionId == null) {

            pan = requestParameters.getRequired(PaymentTrxFields.PAN.getName());
            expiry = requestParameters.getRequired(PaymentTrxFields.EXPIRY.getName());

        } else {

            customerId = CustomerSessionMap.getCustomerId(sessionId);
            cardId = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.CARD_ID.getName()));

        }

        String currency = requestParameters.getRequired(PaymentTrxFields.CURRENCY.getName());
        int value = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.VALUE.getName()));
        return new PaymentTrx(cardId, value, currency, expiry, pan, customerId);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addEntity(req, resp);

    }

}
