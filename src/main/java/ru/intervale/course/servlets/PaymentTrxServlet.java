package ru.intervale.course.servlets;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.PaymentTrxJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@WebServlet("/payment/*")
public class PaymentTrxServlet extends AbstractEntityServlet<PaymentTrx, PaymentTrxJDBCDaoImpl> {

    private enum PaymentFields{
        ID, CARDID, VALUE, CURRENCY
    }


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public IDao<PaymentTrx> getDaoImpl() throws DaoException {
        return DaoFactory.getPaymentTrxDaoImplFromFactory();
    }

    @Override
    protected PaymentTrx parseReqBody(HttpServletRequest req) {
       PaymentTrx paymentTrx = new PaymentTrx();
       Enumeration<String> en = req.getParameterNames();

       while (en.hasMoreElements()) {

           String currentParameter = en.nextElement();
           String currentParameterValue = req.getParameter(currentParameter);
           PaymentFields field = PaymentFields.valueOf(currentParameter.toUpperCase());

           switch (field) {
               case ID:
                   paymentTrx.setId(Integer.valueOf(currentParameterValue));
                   break;
               case CARDID:
                   paymentTrx.setCardId(Integer.valueOf(currentParameterValue));
                   break;
               case VALUE:
                   paymentTrx.setValue(Integer.valueOf(currentParameterValue));
                   break;
               case CURRENCY:
                   paymentTrx.setMoneyCurrency(currentParameterValue);
           }
       }
       return paymentTrx;
    }

}
