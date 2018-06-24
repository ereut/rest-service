package ru.intervale.course;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.*;
import ru.intervale.course.impl.InvalidDataException;
import ru.intervale.course.utils.DatabaseUtils;
import ru.intervale.course.utils.TomcatUtils;

import java.sql.Connection;
import java.util.List;

public class Runner {

    private static void printEntities(String header, List<? extends AbstractEntity> entitiesList) {
        System.out.println(header);
        for (AbstractEntity abstractEntity : entitiesList) {
            System.out.println(abstractEntity);
        }
        System.out.println();
    }

    private static Logger log = (Logger) LoggerFactory.getLogger(Runner.class);

    public static void main(String args[]) {

        try {
            IDao<Customer> customerIDao = DaoFactory.getCustomerDaoImplFromFactory();
            IDao<Card> cardIDao = DaoFactory.getCardDaoImplFromFactory();
            IDao<PaymentTrx> paymentTrxIDao = DaoFactory.getPaymentTrxDaoImplFromFactory();

            //customers
            Customer customerYauheni = customerIDao.persist(new Customer("yauheni",
                    "1111", null, null, null, null));

            Customer customerNick = customerIDao.persist(new Customer("nick",
                    "2222", null, null, null, null));

            Customer customerArseniy = customerIDao.persist(new Customer("arseniy",
                    "3333", null, null, null, null));

            //cards
            Card customerYauheniCard = cardIDao.persist(new Card(customerYauheni.getId(),
                    "1111 1111 1111 1111","1220","yauheniCard"));
            Card customerNickCard = cardIDao.persist(new Card(customerNick.getId(),
                    "2222 1111 1111 1111", "1019", "nickCard"));
            Card customerArseniyCard =  cardIDao.persist(new Card(customerArseniy.getId(),
                    "2222 1111 1111 1111", "0221", "arseniyCard"));

            //payments
            PaymentTrx paymentTrx1 = paymentTrxIDao.persist(new PaymentTrx(customerYauheniCard.getId(),
                    300_00, "byn", null, null, customerYauheni.getId()));
            PaymentTrx paymentTrx2 = paymentTrxIDao.persist(new PaymentTrx(customerYauheniCard.getId(),
                    325_58, "usd", null, null, customerYauheni.getId()));
            PaymentTrx paymentTrx3 = paymentTrxIDao.persist(new PaymentTrx(customerYauheniCard.getId(),
                    2_58, "euro", null, null, customerYauheni.getId()));
            PaymentTrx paymentTrx4 = paymentTrxIDao.persist( new PaymentTrx(customerNickCard.getId(),
                    100_05, "euro", null, null, customerNick.getId()));
            PaymentTrx paymentTrx5 = paymentTrxIDao.persist(new PaymentTrx(customerNickCard.getId(),
                    99_99, "usd", null, null, customerNick.getId()));
            PaymentTrx paymentTrx6 = paymentTrxIDao.persist(new PaymentTrx(customerNickCard.getId(),
                    999_99, "byn", null, null, customerNick.getId()));
            PaymentTrx paymentTrx7 = paymentTrxIDao.persist(new PaymentTrx(customerArseniyCard.getId(),
                    999_99, "byn", null, null, customerArseniy.getId()));
            PaymentTrx paymentTrx8 = paymentTrxIDao.persist(new PaymentTrx(customerArseniyCard.getId(),
                    5_99, "euro", null, null, customerArseniy.getId()));
            PaymentTrx paymentTrx9 = paymentTrxIDao.persist(new PaymentTrx(customerArseniyCard.getId(),
                    5_99, "usd", null, null, customerArseniy.getId()));

            printEntities(Constants.CUSTOMERS_PRINT_HEADER, customerIDao.getAll());
            printEntities(Constants.CARDS_PRINT_HEADER, cardIDao.getAll());
            printEntities(Constants.PAYMENTS_PRINT_HEADER, paymentTrxIDao.getAll());

            Connection cn = DaoFactory.getJDBCConnection();

            DatabaseUtils.printTrxSum(cn);
            DatabaseUtils.printPaymentsByCustomers(cn);

            TomcatUtils.runTomcatEmbedded();

        } catch (DaoException | InvalidDataException e) {
            log.error(e.getMessage());
        }

    }
}
