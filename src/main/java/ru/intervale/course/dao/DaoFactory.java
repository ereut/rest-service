package ru.intervale.course.dao;

import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DaoFactory {

    private static final String DATABASE_FILE_NAME = "db";
    private static final ResourceBundle RB = ResourceBundle.getBundle(DATABASE_FILE_NAME);

    private static String MY_SQL_DATABASE_URL = RB.getString("db.mysql.url");
    private static String MY_SQL_DATABASE_USER = RB.getString("db.mysql.user");
    private static String MY_SQL_DATABASE_PASSWORD = RB.getString("db.mysql.password");

    private static String H2_DATABASE_URL = RB.getString("db.h2.url");
    private static String H2_SQL_DATABASE_USER = RB.getString("db.h2.user");
    private static String H2_SQL_DATABASE_PASSWORD = RB.getString("db.h2.password");

    private static Connection connection;


    private enum DaoImpls {

        MY_SQL {
            @Override
            IDao<Customer> getCustomerDaoImpl() {
                return null;
            }

            @Override
            IDao<Card> getCardDaoImpl() {
                return null;
            }

            @Override
            IDao<PaymentTrx> getPaymentTrxDaoImpl() {
                return null;
            }

            @Override
            Connection getConnection() throws DaoException {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(MY_SQL_DATABASE_URL,
                                MY_SQL_DATABASE_USER, MY_SQL_DATABASE_PASSWORD);
                        DatabaseUtils.runLiquibase(connection);
                        connection.setAutoCommit(true);
                    } catch (SQLException e) {
                        throw new DaoException(e);
                    }
                }
                return connection;
            }
        }, H2 {
            @Override
            IDao<Customer> getCustomerDaoImpl() {
                return null;
            }

            @Override
            IDao<Card> getCardDaoImpl() {
                return null;
            }

            @Override
            IDao<PaymentTrx> getPaymentTrxDaoImpl() {
                return null;
            }

            @Override
            Connection getConnection() {
                return null;
            }
        };

        abstract  IDao<Customer> getCustomerDaoImpl();
        abstract IDao<Card> getCardDaoImpl();
        abstract IDao<PaymentTrx> getPaymentTrxDaoImpl();

        abstract Connection getConnection() throws SQLException, DaoException;

    }

    public static IDao<Customer> getCustomerDaoImplFromFactory() {

    }




}
