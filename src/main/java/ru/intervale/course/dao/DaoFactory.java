package ru.intervale.course.dao;

import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.impl.CardJDBCDaoImpl;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;
import ru.intervale.course.impl.PaymentTrxJDBCDaoImpl;
import ru.intervale.course.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DaoFactory {

    static {
        final String DEFAULT_DATABASE_TYPE = "my_sql";
        final String INSTALL_DEFAULT_DATABASE_TYPE_MESSAGE = "Default type of database MySql was installed";
        final String DATABASE_FILE_NAME = "db";
        final String DATABASE_TYPE = "db.type";
        final ResourceBundle RB  = ResourceBundle.getBundle(DATABASE_FILE_NAME);

        MY_SQL_DATABASE_URL = RB.getString("db.mysql.url");
        MY_SQL_DATABASE_USER = RB.getString("db.mysql.user");
        MY_SQL_DATABASE_PASSWORD = RB.getString("db.mysql.password");

        H2_DATABASE_URL = RB.getString("db.h2.url");
        H2_DATABASE_USER = RB.getString("db.h2.user");
        H2_SQL_DATABASE_PASSWORD = RB.getString("db.h2.password");

        String daoImplTypeString = DEFAULT_DATABASE_TYPE;
        daoImplType = DaoImplTypes.valueOf(daoImplTypeString.toUpperCase());

        try {
            daoImplType = DaoImplTypes.valueOf(RB.getString(DATABASE_TYPE).toUpperCase());
        } catch (Exception e) {
            LoggerFactory.getLogger(DaoFactory.class).debug(INSTALL_DEFAULT_DATABASE_TYPE_MESSAGE);
        }

    }

    private static DaoImplTypes daoImplType;

    private static String MY_SQL_DATABASE_URL;
    private static String MY_SQL_DATABASE_USER;
    private static String MY_SQL_DATABASE_PASSWORD;

    private static String H2_DATABASE_URL;
    private static String H2_DATABASE_USER;
    private static String H2_SQL_DATABASE_PASSWORD;

    private static Connection connection;

    private enum DaoImplTypes {

        MY_SQL {
            @Override
            IDao<Customer> getCustomerDaoImpl() throws DaoException {
                return new CustomerJDBCDaoImpl(getJDBCConnection());
            }

            @Override
            IDao<Card> getCardDaoImpl() throws DaoException {
                return new CardJDBCDaoImpl(getJDBCConnection());
            }

            @Override
            IDao<PaymentTrx> getPaymentTrxDaoImpl() throws DaoException {
                return new PaymentTrxJDBCDaoImpl(getJDBCConnection());
            }

            @Override
            Connection getConnection() throws SQLException {
                return DriverManager.getConnection(MY_SQL_DATABASE_URL,
                                MY_SQL_DATABASE_USER, MY_SQL_DATABASE_PASSWORD);
            }
        }, H2 {
            @Override
            IDao<Customer> getCustomerDaoImpl() throws DaoException {
                return new CustomerJDBCDaoImpl(getJDBCConnection());
            }

            @Override
            IDao<Card> getCardDaoImpl() throws DaoException {
                return new CardJDBCDaoImpl(getJDBCConnection());
            }

            @Override
            IDao<PaymentTrx> getPaymentTrxDaoImpl() throws DaoException {
                return new PaymentTrxJDBCDaoImpl(getJDBCConnection());
            }

            @Override
            Connection getConnection() throws SQLException {
                return DriverManager.getConnection(H2_DATABASE_URL,
                        H2_DATABASE_USER, H2_SQL_DATABASE_PASSWORD);
            }
        };

        abstract  IDao<Customer> getCustomerDaoImpl() throws DaoException;
        abstract IDao<Card> getCardDaoImpl() throws DaoException;
        abstract IDao<PaymentTrx> getPaymentTrxDaoImpl() throws DaoException;

        abstract Connection getConnection() throws DaoException, SQLException;

    }

     public static Connection getJDBCConnection() throws DaoException {
        if (connection == null) {
            try {
                connection = daoImplType.getConnection();
                DatabaseUtils.runLiquibase(connection);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return connection;
    }

    public static IDao<Customer> getCustomerDaoImplFromFactory() throws DaoException {
        return daoImplType.getCustomerDaoImpl();
    }

    public static IDao<Card> getCardDaoImplFromFactory() throws DaoException {
        return daoImplType.getCardDaoImpl();
    }

    public static IDao<PaymentTrx> getPaymentTrxDaoImplFromFactory() throws DaoException {
        return daoImplType.getPaymentTrxDaoImpl();
    }

}
