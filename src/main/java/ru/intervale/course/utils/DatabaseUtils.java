package ru.intervale.course.utils;

import ru.intervale.course.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class DatabaseUtils {

    private static final String SELECT_SUM_PAYMENTS_QUERY =
            "SELECT SUM(value) FROM customers.payments WHERE currency = 'byn'";

    private static final String SELECT_PAYMENTS_BY_CUSTOMERS =
            "SELECT customers.id, customers.name, customers.surname, cards.pan, payments.value, payments.currency " +
                    "FROM customers.customers " +
                    "JOIN customers.cards ON customers.id = cards.customerId " +
                    "JOIN customers.payments ON cards.id = payments.cardId " +
                    "ORDER BY id, value DESC";

    public static void printTrxSum(Connection cn) throws DaoException {

        try(PreparedStatement pst = cn.prepareStatement(SELECT_SUM_PAYMENTS_QUERY)) {

            ResultSet rs = pst.executeQuery();
            rs.next();
            int sum = rs.getInt(1);
            System.out.printf(Locale.ENGLISH, "Sum of all byn payments is %s byn%n",
                    CurrencyUtils.getStringCurrencyValue(sum));
            System.out.println();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static void printPaymentsByCustomers(Connection cn)
            throws DaoException {

        try(PreparedStatement pst = cn.prepareStatement(SELECT_PAYMENTS_BY_CUSTOMERS)) {
            System.out.println("All sorted payments by every customer");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {

                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                String pan = rs.getString(4);
                int value = rs.getInt(5);
                String currency = rs.getString(6);
                System.out.printf(Locale.ENGLISH, "|%-5d|%-10s|%-10s|%-22s|%-10s|%-5s|%n",
                        id, name, surname, pan, CurrencyUtils.getStringCurrencyValue(value), currency);
            }
            System.out.println();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

}
