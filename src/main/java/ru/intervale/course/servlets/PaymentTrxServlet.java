package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.PaymentTrxJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment/*")
public class PaymentTrxServlet extends AbstractEntityServlet<PaymentTrx, PaymentTrxJDBCDaoImpl> {

    @Override
    protected PaymentTrx parseReqBody(HttpServletRequest req) throws InvalidRequestException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected IDao<PaymentTrx> getDaoImpl() throws DaoException {
        return DaoFactory.getPaymentTrxDaoImplFromFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        getEntity(req, resp);

    }

}
