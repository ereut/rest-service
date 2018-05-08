package ru.intervale.course.dao;

import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionFactory {

    private static Connection connection;

    private enum DaoEnum {
        MY_SQL {
            @Override
            Connection getConnection() throws DaoException {
                try {
                    Properties properties = new Properties();
                    properties.put("user", "root");
                    properties.put("password", "1111");
                    final String MY_SQL_DB_URL = "jdbc:mysql://localhost/customers?" +
                            "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    connection = DriverManager.getConnection(MY_SQL_DB_URL, properties);
                } catch (SQLException e) {
                    throw new DaoException("Problems with connection to MySQL database", e) ;
                }
                return connection;
            }
        }, H2 {
            @Override
            Connection getConnection() {

                return null;
            }
        };

       abstract Connection getConnection() throws DaoException;
    }

    public static Connection getConnection() throws DaoException {
        final String DEFAULT_DATABASE_VALUE = "my_sql";
        String dao = DEFAULT_DATABASE_VALUE;
        try {
            dao = ResourceBundle.getBundle("database").getString("Ddatabase");
        } catch (MissingResourceException e) {
            LoggerFactory.getLogger(ConnectionFactory.class).debug("Key Ddatabase was not found, " +
                            "set default value {}", DEFAULT_DATABASE_VALUE);
        }
        return DaoEnum.valueOf(dao.toUpperCase()).getConnection();

    }

}
