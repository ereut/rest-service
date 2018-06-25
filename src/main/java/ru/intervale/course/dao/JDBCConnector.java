package ru.intervale.course.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCConnector {

    private final static String DATABASE_FILE_NAME = "db";
    private final static ResourceBundle RB = ResourceBundle.getBundle(DATABASE_FILE_NAME);
    private static Logger logger = LoggerFactory.getLogger(JDBCConnector.class);

    private static DataBases databaseType;

    static {
        try {
            databaseType = DataBases.valueOf(RB.getString("db.type").toUpperCase());
        } catch (Exception e) {
            databaseType = DataBases.MY_SQL;
        }
        logger.info("Current database is {}", databaseType);
    }

    private final static String MY_SQL_DATABASE_URL = RB.getString("db.mysql.url");
    private final static String MY_SQL_DATABASE_USER = RB.getString("db.mysql.user");
    private final static String MY_SQL_DATABASE_PASSWORD = RB.getString("db.mysql.password");

    private final static String H2_DATABASE_URL = RB.getString("db.h2.url");
    private final static String H2_DATABASE_USER = RB.getString("db.h2.user");
    private final static String H2_SQL_DATABASE_PASSWORD = RB.getString("db.h2.password");

    private enum DataBases {
        H2 {
            @Override
            Connection getConnection() throws SQLException {
                return DriverManager.getConnection(H2_DATABASE_URL, H2_DATABASE_USER, H2_SQL_DATABASE_PASSWORD);
            }

        }, MY_SQL {
            @Override
            Connection getConnection() throws SQLException {
                return DriverManager.getConnection(MY_SQL_DATABASE_URL, MY_SQL_DATABASE_USER, MY_SQL_DATABASE_PASSWORD);
            }
        };

        abstract Connection getConnection() throws SQLException;
    }


    public static Connection getJDBCConnection() throws DaoException {
        try {
            return databaseType.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
















