package ru.intervale.course.servlets;


import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.dao.PaymentTrxJDBCDao;

import javax.servlet.annotation.WebServlet;

@WebServlet("/payment/*")
public class PaymentTrxServlet extends AbstractServlet<PaymentTrx, PaymentTrxJDBCDao>{

    @Override
    protected IDao<PaymentTrx> getDaoImpl() {
        return new PaymentTrxJDBCDao(getConnection());
    }

}
