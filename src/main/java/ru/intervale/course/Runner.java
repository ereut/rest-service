package ru.intervale.course;

import ru.intervale.course.database.MySQLConnection;
import ru.intervale.course.database.Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Runner {

    public static void main(String args[]) {

        final String CREATE_CARDS_TABLE_QUERY =
                "CREATE TABLE `customers`.`cards` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `customerId` INT NOT NULL,\n" +
                        "  `cardPan` VARCHAR(45) NOT NULL,\n" +
                        "  `cardExpiry` DATE NOT NULL,\n" +
                        "  `cardRegisterTime` DATETIME NOT NULL,\n" +
                        "  PRIMARY KEY (`id`));\n";

        final String CREATE_PAYMENTS_TABLE_QUERY =
                "CREATE TABLE `customers`.`payments` (\n" +
                        "  `id` INT NOT NULL,\n" +
                        "  `cardId` INT NOT NULL,\n" +
                        "  `paymentStartTime` DATETIME NOT NULL,\n" +
                        "  `paymentFinishTime` DATETIME NOT NULL,\n" +
                        "  `paymentValue` INT NOT NULL,\n" +
                        "  `paymentCurrency` VARCHAR(45) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC));";

        try (Connection cn = MySQLConnection.getMySQLConnection()) {
           PreparedStatement pst = cn.prepareStatement(Constants.CREATE_CUSTOMERS_TABLE_QUERY);
           pst.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        };

    }
}
