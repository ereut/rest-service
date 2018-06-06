package ru.intervale.course.servlets;


import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.dao.PaymentTrxJDBCDao;

import javax.servlet.annotation.WebServlet;

@WebServlet("/payment/*")
public class PaymentTrxServlet extends AbstractServlet<PaymentTrx, PaymentTrxJDBCDao> {

    private enum PaymentFields{
        ID, CARDID, VALUE, CURRENCY
    }

    @Override
    protected IDao<PaymentTrx> getDaoImpl() {
        return new PaymentTrxJDBCDao(getConnection());
    }

    @Override
    protected PaymentTrx parseReqBody(String body) {

        PaymentTrx paymentTrx = new PaymentTrx();

        for (String str : body.split("\n")) {

            PaymentFields field = PaymentFields.valueOf(getKeyFromLine(str).toUpperCase());

            switch (field) {
                case ID:
                    paymentTrx.setId(Integer.valueOf(getValueFromLine(str).trim()));
                    break;
                case CARDID:
                    paymentTrx.setCardId(Integer.valueOf(getValueFromLine(str).trim()));
                    break;
                case VALUE:
                    paymentTrx.setValue(Integer.valueOf(getValueFromLine(str).trim()));
                    break;
                case CURRENCY:
                    paymentTrx.setMoneyCurrency(getValueFromLine(str).trim());
            }
        }

        return paymentTrx;
    }
}
