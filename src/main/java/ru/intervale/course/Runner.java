package ru.intervale.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.*;
import ru.intervale.course.dao.interfaces.ICardDao;
import ru.intervale.course.dao.interfaces.ICustomerDao;
import ru.intervale.course.dao.interfaces.IPaymentTrxDao;
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



        try (Connection cn = DBConnector.getConnection()) {
            DatabaseUtils.createSchemaAndTables(cn);
            ICustomerDao customerJDBCDao = new CustomerJDBCDao(cn);
            ICardDao cardJDBCDao = new CardJDBCDao(cn);
            IPaymentTrxDao paymentTrxJDBCDao = new PaymentTrxJDBCDao(cn);
/*
            customerJDBCDao.create("Yauheni","Reut","297336763",
                    "Belarus", "Gomel", "Kosmonavtov avenue",
                    "67", "262");
            customerJDBCDao.create("Nick","Ivanov","447836865",
                    "Belarus", "Minsk", "Masherov avenue",
                    "135a", "2");
            customerJDBCDao.create("Arseniy","Petrov","447666753",
                    "Russia", "Moskov", "Sadovaya street",
                    "100", "55");
            customerJDBCDao.create("Alex","Sumniy","336452287",
                    "Russia", "St.Peterburg", "Nikitina street",
                    "1110", "210");
            customerJDBCDao.create("Alex","Lebedkin","447822287",
                    "Belarus", "Brest", "Kosmonavtov avenue",
                    "110", "100");
            customerJDBCDao.create("Mikhail","Vilchuk","337856589",
                    "Belarus", "Minsk", "Ptitickiy street",
                    "66", "6");

            cardJDBCDao.create(1, "1111 1111 1111 1111", "1220");
            cardJDBCDao.create(2, "2222 1111 1111 1111", "0721");
            cardJDBCDao.create(3, "3333 1111 1111 1111", "0122");
            cardJDBCDao.create(4, "4444 1111 1111 1111", "1020");
            cardJDBCDao.create(5, "5555 1111 1111 1111", "0210");
            cardJDBCDao.create(6, "6666 1111 1111 1111", "0119");
            cardJDBCDao.create(6, "1111 7777 1111 1111", "0819");
            cardJDBCDao.create(5, "2222 8888 1111 1111", "1020");
            cardJDBCDao.create(4, "1111 9999 1111 1111", "0821");
            cardJDBCDao.create(2, "2222 1111 2222 1111", "0619");
            cardJDBCDao.create(1, "1111 1111 3232 1111", "0718");

            paymentTrxJDBCDao.create(1, 328_00, "byn");
            paymentTrxJDBCDao.create(1, 550_00, "usd");
            paymentTrxJDBCDao.create(2, 1353_00, "byn");
            paymentTrxJDBCDao.create(3, 125_00, "byn");
            paymentTrxJDBCDao.create(5, 305_60, "usd");
            paymentTrxJDBCDao.create(7, 418_28, "euro");
            paymentTrxJDBCDao.create(11, 199_99, "euro");
            paymentTrxJDBCDao.create(9, 115_15, "byn");
            paymentTrxJDBCDao.create(8, 267_10, "usd");
            paymentTrxJDBCDao.create(6, 114_25, "euro");
            paymentTrxJDBCDao.create(6, 225_20, "byn");
            paymentTrxJDBCDao.create(6, 100_45, "usd");
            paymentTrxJDBCDao.create(7, 2_00, "usd");
            paymentTrxJDBCDao.create(9, 87_18, "euro");
            paymentTrxJDBCDao.create(5, 147_00, "byn");
            paymentTrxJDBCDao.create(8, 1145_38, "byn");
            paymentTrxJDBCDao.create(4, 14_25, "euro");
            paymentTrxJDBCDao.create(3, 169_45, "usd");

*/

            printEntities(Constants.CUSTOMERS_HEADER, customerJDBCDao.getAll());
            printEntities(Constants.CARDS_HEADER, cardJDBCDao.getAll());
            printEntities(Constants.PAYMENTS_HEADER, paymentTrxJDBCDao.getAll());

            DatabaseUtils.printTrxSum(cn);
            DatabaseUtils.printPaymentsByCustomers(cn);


        } catch (SQLException | DaoException e) {
            log.error(e.getMessage());
        }

    }
}
