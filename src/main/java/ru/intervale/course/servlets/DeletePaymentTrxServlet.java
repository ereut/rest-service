package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.servlets.enums.PaymentTrxFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePaymentTrxServlet extends PaymentTrxServlet {

    @Override
    protected PaymentTrx parseReqBody(HttpServletRequest req) {
        RequestParameters requestParameters = new RequestParameters(req);
        int id = Integer.parseInt(requestParameters.getRequired(PaymentTrxFields.ID.getName()));
        PaymentTrx paymentTrx = new PaymentTrx();
        paymentTrx.setId(id);
        return paymentTrx;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        deleteEntity(req, resp);
    }

}
