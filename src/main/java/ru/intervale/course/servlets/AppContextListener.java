package ru.intervale.course.servlets;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.JDBCConnector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener{

    private static Logger logger = LoggerFactory.getLogger(AppContextListener.class);
    private static final String LIQUIBASE_FILE_NAME = "liquibase/db.changelog-master.xml";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application context was initialized");
        try {
            runLiquibase();
        } catch (DaoException e) {
           logger.error("Error during run liquibase");
        }
    }

    public static void runLiquibase() throws DaoException {
        try (Connection cn = JDBCConnector.getJDBCConnection()) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(cn));
            Liquibase liquibase = new liquibase.Liquibase(LIQUIBASE_FILE_NAME,
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException | SQLException e) {
            throw new DaoException(e);
        }
    }

}
