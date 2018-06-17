package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.PaymentTrxJDBCDaoImpl;

import javax.servlet.ServletException;

public abstract class PaymentTrxServlet extends AbstractEntityServlet<PaymentTrx, PaymentTrxJDBCDaoImpl> {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected IDao<PaymentTrx> getDaoImpl() throws DaoException {
        return DaoFactory.getPaymentTrxDaoImplFromFactory();
    }

}
