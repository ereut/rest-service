package ru.intervale.course.servlets;


import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.dao.PaymentTrxJDBCDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/payment/*")
public class PaymentTrxServlet extends AbstractServlet<PaymentTrx, PaymentTrxJDBCDao>{

    @Override
    protected IDao<PaymentTrx> getDaoImpl() {
        return new PaymentTrxJDBCDao(getConnection());
    }

    @Override
    protected PaymentTrx parseAddReqBody(String body) {
        return null;
    }

    @Override
    protected PaymentTrx parseUpdateReqBody(String body) {
        return null;
    }


}
