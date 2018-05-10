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

    private static final String CREATE_CUSTOMERS_SCHEMA =
            "CREATE SCHEMA IF NOT EXISTS customers";

    private static final String SELECT_PAYMENTS_BY_CUSTOMERS =
            "SELECT customers.id, customers.name, customers.surname, cards.pan, payments.value, payments.currency \n" +
                    "FROM customers.customers \n" +
                    "JOIN customers.cards ON customers.id = cards.customerId \n" +
                    "JOIN customers.payments ON cards.id = payments.cardId \n" +
                    "ORDER BY id, value DESC";

    private static final String CREATE_CUSTOMERS_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS`customers`.`customers` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `surname` VARCHAR(45) NOT NULL,\n" +
                    "  `telephoneNumber` VARCHAR(45) NOT NULL,\n" +
                    "  `country` VARCHAR(45) NOT NULL,\n" +
                    "  `city` VARCHAR(45) NOT NULL,\n" +
                    "  `street` VARCHAR(45) NOT NULL,\n" +
                    "  `homeNumber` VARCHAR(45) NOT NULL,\n" +
                    "  `flatNumber` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id`));";

    private static final String CREATE_CARDS_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `customers`.`cards` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `customerId` INT NOT NULL,\n" +
                    "  `pan` VARCHAR(45) NOT NULL,\n" +
                    "  `expiry` VARCHAR(45) NOT NULL,\n" +
                    "  `registerTime` TIME NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))";

    private static final String CREATE_PAYMENTS_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `customers`.`payments` (\n" +
                    "                    `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "                    `cardId` INT NOT NULL,\n" +
                    "                    `startTime` TIME NOT NULL,\n" +
                    "                    `finishTime` TIME NOT NULL,\n" +
                    "                    `value` INT NOT NULL,\n" +
                    "                    `currency` VARCHAR(45) NOT NULL,\n" +
                    "                    PRIMARY KEY (`id`))";

    public static void createSchemaAndTables(Connection connection) throws DaoException {

        try (   PreparedStatement schemaPst =
                     connection.prepareStatement(CREATE_CUSTOMERS_SCHEMA);
                PreparedStatement customersTablePst =
                     connection.prepareStatement(CREATE_CUSTOMERS_TABLE_QUERY);
             PreparedStatement cardsTablePst =
                     connection.prepareStatement(CREATE_CARDS_TABLE_QUERY);
             PreparedStatement paymentsTablePst =
                     connection.prepareStatement(CREATE_PAYMENTS_TABLE_QUERY)) {
            schemaPst.executeUpdate();
            customersTablePst.executeUpdate();
            cardsTablePst.executeUpdate();
            paymentsTablePst.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

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
