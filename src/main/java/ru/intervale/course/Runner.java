package ru.intervale.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.*;
import ru.intervale.course.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Runner {

    private static void printEntities(String header,
                                      List<? extends AbstractEntity> entitiesList) {
        System.out.println(header);
        for (AbstractEntity abstractEntity : entitiesList) {
            System.out.println(abstractEntity);
        }
        System.out.println();
    }

    private static Logger log = LoggerFactory.getLogger(Runner.class);

    public static void main(String args[]) {

        try (Connection cn = JDBCConnector.getConnection()) {

            DatabaseUtils.createSchemaAndTables(cn);

            IDao<Customer> customerIDao = new CustomerJDBCDao(cn);
            IDao<Card> cardIDao = new CardJDBCDao(cn);
            IDao<PaymentTrx> paymentTrxIDao = new PaymentTrxJDBCDao(cn);

            //customers
            Customer customerYauheni =
                    new Customer("Yauheni","Reut","297336763",
                    "Belarus", "Gomel", "Kosmonavtov avenue",
                    "67", "262");
            Customer customerNick =
                    new Customer("Nick","Ivanov","447836865","Belarus", "Minsk", "Masherov avenue",
                    "135a", "2");
            Customer customerArseniy =
                    new Customer("Arseniy","Petrov","447666753",
                            "Russia", "Moskov", "Sadovaya street",
                            "100", "55");

            customerYauheni = customerIDao.persist(customerYauheni);
            customerNick = customerIDao.persist(customerNick);
            customerArseniy = customerIDao.persist(customerArseniy);

            //cards
            Card customerYauheniCard =
                    new Card(customerYauheni.getId(), "1111 1111 1111 1111",
                    "1220");
            Card customerNickCard =
                    new Card(customerNick.getId(), "2222 1111 1111 1111",
                            "1019");
            Card customerArseniyCard =
                    new Card(customerArseniy.getId(), "2222 1111 1111 1111",
                            "0221");

            customerYauheniCard = cardIDao.persist(customerYauheniCard);
            customerArseniyCard = cardIDao.persist(customerArseniyCard);
            customerNickCard = cardIDao.persist(customerNickCard);

            //payments
            PaymentTrx paymentTrx1 =
                    new PaymentTrx(customerYauheniCard.getId(), 300_00, "byn");
            PaymentTrx paymentTrx2 =
                    new PaymentTrx(customerYauheniCard.getId(), 325_58, "usd");
            PaymentTrx paymentTrx3 =
                    new PaymentTrx(customerYauheniCard.getId(), 2_58, "euro");
            PaymentTrx paymentTrx4 =
                    new PaymentTrx(customerNickCard.getId(), 100_05, "euro");
            PaymentTrx paymentTrx5 =
                    new PaymentTrx(customerNickCard.getId(), 99_99, "usd");
            PaymentTrx paymentTrx6 =
                    new PaymentTrx(customerNickCard.getId(), 999_99, "byn");
            PaymentTrx paymentTrx7 =
                    new PaymentTrx(customerArseniyCard.getId(), 999_99, "byn");
            PaymentTrx paymentTrx8 =
                    new PaymentTrx(customerArseniyCard.getId(), 5_99, "euro");
            PaymentTrx paymentTrx9 =
                    new PaymentTrx(customerArseniyCard.getId(), 5_99, "usd");

            paymentTrx1 = paymentTrxIDao.persist(paymentTrx1);
            paymentTrx2 = paymentTrxIDao.persist(paymentTrx2);
            paymentTrx3 = paymentTrxIDao.persist(paymentTrx3);
            paymentTrx4 = paymentTrxIDao.persist(paymentTrx4);
            paymentTrx5 = paymentTrxIDao.persist(paymentTrx5);
            paymentTrx6 = paymentTrxIDao.persist(paymentTrx6);
            paymentTrx7 = paymentTrxIDao.persist(paymentTrx7);
            paymentTrx8 = paymentTrxIDao.persist(paymentTrx8);
            paymentTrx9 = paymentTrxIDao.persist(paymentTrx9);

            printEntities(Constants.CUSTOMERS_HEADER, customerIDao.getAll());
            printEntities(Constants.CARDS_HEADER, cardIDao.getAll());
            printEntities(Constants.PAYMENTS_HEADER, paymentTrxIDao.getAll());

            DatabaseUtils.printTrxSum(cn);
            DatabaseUtils.printPaymentsByCustomers(cn);

        } catch (SQLException | DaoException e) {
            log.error(e.getMessage());
        }

    }
}
