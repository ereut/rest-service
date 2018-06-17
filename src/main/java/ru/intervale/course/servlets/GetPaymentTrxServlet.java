package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetPaymentTrxServlet extends PaymentTrxServlet {

    @Override
    protected PaymentTrx parseReqBody(HttpServletRequest req) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        getEntity(req, resp);

    }
}