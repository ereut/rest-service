package ru.intervale.course.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener{

    private static Logger logger = LoggerFactory.getLogger(AppContextListener.class);
    private static final String PROBLEMS_WITH_CLOSE_CONNECTION = "Problems with connection closing";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application context was initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        try {
            logger.info("JDBC connection was closed and application context was destroyed");
            DaoFactory.closeJDBCConnection();

        } catch (DaoException e) {
            logger.error(PROBLEMS_WITH_CLOSE_CONNECTION, e);
        }

    }

}
