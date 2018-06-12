package ru.intervale.course;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.*;
import ru.intervale.course.utils.DatabaseUtils;
import ru.intervale.course.utils.TomcatUtils;

import java.sql.Connection;
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

    private static Logger log = (Logger) LoggerFactory.getLogger(Runner.class);

    public static void main(String args[]) {

        try {
            IDao<Customer> customerIDao = DaoFactory.getCustomerDaoImplFromFactory();
            IDao<Card> cardIDao = DaoFactory.getCardDaoImplFromFactory();
            IDao<PaymentTrx> paymentTrxIDao = DaoFactory.getPaymentTrxDaoImplFromFactory();

            //customers
            Customer customerYauheni = customerIDao.persist(new Customer("Yauheni","Reut",
                    "297336763",
                    "Belarus", "Gomel", "Kosmonavtov avenue",
                    "67", "262"));

            Customer customerNick = customerIDao.persist(new Customer("Nick","Ivanov",
                    "447836865","Belarus", "Minsk", "Masherov avenue",
                    "135a", "2"));

            Customer customerArseniy = customerIDao.persist(new Customer("Arseniy","Petrov",
                    "447666753", "Russia", "Moskov", "Sadovaya street",
                    "100", "55"));

            //cards
            Card customerYauheniCard = cardIDao.persist(new Card(customerYauheni.getId(),
                    "1111 1111 1111 1111","1220","yauheniCard"));
            Card customerNickCard = cardIDao.persist(new Card(customerNick.getId(),
                    "2222 1111 1111 1111", "1019", "nickCard"));
            Card customerArseniyCard =
                    new Card(customerArseniy.getId(), "2222 1111 1111 1111",
                            "0221", "arseniyCard");

            //payments
            PaymentTrx paymentTrx1 = paymentTrxIDao.persist(new PaymentTrx(customerYauheniCard.getId(),
                    300_00, "byn"));
            PaymentTrx paymentTrx2 = paymentTrxIDao.persist(new PaymentTrx(customerYauheniCard.getId(),
                    325_58, "usd"));
            PaymentTrx paymentTrx3 = paymentTrxIDao.persist(new PaymentTrx(customerYauheniCard.getId(),
                    2_58, "euro"));
            PaymentTrx paymentTrx4 = paymentTrxIDao.persist( new PaymentTrx(customerNickCard.getId(),
                    100_05, "euro"));
            PaymentTrx paymentTrx5 = paymentTrxIDao.persist(new PaymentTrx(customerNickCard.getId(),
                    99_99, "usd"));
            PaymentTrx paymentTrx6 = paymentTrxIDao.persist(new PaymentTrx(customerNickCard.getId(),
                    999_99, "byn"));
            PaymentTrx paymentTrx7 = paymentTrxIDao.persist(new PaymentTrx(customerArseniyCard.getId(),
                    999_99, "byn"));
            PaymentTrx paymentTrx8 = paymentTrxIDao.persist(new PaymentTrx(customerArseniyCard.getId(),
                    5_99, "euro"));
            PaymentTrx paymentTrx9 = paymentTrxIDao.persist(new PaymentTrx(customerArseniyCard.getId(),
                    5_99, "usd"));

            printEntities(Constants.CUSTOMERS_PRINT_HEADER, customerIDao.getAll());
            printEntities(Constants.CARDS_PRINT_HEADER, cardIDao.getAll());
            printEntities(Constants.PAYMENTS_PRINT_HEADER, paymentTrxIDao.getAll());

            Connection cn = DaoFactory.getJDBCConnection();

            DatabaseUtils.printTrxSum(cn);
            DatabaseUtils.printPaymentsByCustomers(cn);

            TomcatUtils.runTomcatEmbedded();

        } catch (DaoException e) {
            log.error(e.getMessage());
        }

    }
}
