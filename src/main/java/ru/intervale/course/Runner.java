package ru.intervale.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

public class Runner {

    private static Logger log = LoggerFactory.getLogger(Runner.class);

    public static void main(String args[]) {



        try (Connection cn = ConnectionFactory.getConnection()) {
            CustomerDao customerIDao = new CustomerDao(cn);
            CardDao cardIDao = new CardDao(cn);
            cardIDao.create(1, "1121 9899 1111 1111", "1019");
            /*IDao<PaymentTrx> paymentTrxIDao = new PaymentTrxDao(cn);
            System.out.println(customerIDao.getAll());
            System.out.println(customerIDao.getEntityById(44));*/

        } catch (SQLException | DaoException e) {
            log.error(e.getMessage());
        }

    }
}
