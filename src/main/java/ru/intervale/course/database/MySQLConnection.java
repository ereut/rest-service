package ru.intervale.course.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private MySQLConnection() {
    }

    private static Connection connection = null;

    private static Logger log = LoggerFactory.getLogger(MySQLConnection.class);

    public static Connection getMySQLConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(Constants.DBURL, Constants.USER_NAME,
                        Constants.USER_PASSWORD);
            } catch (SQLException e) {
               log.error("Problem with connection to database", e);
            }
        }
        return connection;
    }

}
