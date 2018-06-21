package ru.intervale.course.servlets;

import org.slf4j.LoggerFactory;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    private static final String PROBLEMS_WITH_CLOSE_CONNECTION = "Problems with close connection";

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DaoFactory.closeJDBCConnection();
        } catch (DaoException e) {
            LoggerFactory.getLogger(AppContextListener.class).error(PROBLEMS_WITH_CLOSE_CONNECTION);
        }

    }

}
