package ru.intervale.course.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.AbstractJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/*")
public abstract class AbstractServlet<T extends AbstractEntity, K extends AbstractJDBCDaoImpl<T>> extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(AbstractServlet.class);
    private IDao<T> iDaoImpl;

    private static String PROBLEMS_WITH_CREATION_DAO_IMPL_MESSAGE =
            "Problems with creation dao implementation";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            iDaoImpl = getDaoImpl();
        } catch (DaoException e) {
           logger.error(PROBLEMS_WITH_CREATION_DAO_IMPL_MESSAGE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            System.out.println("From method doGet()" + iDaoImpl.getAll());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("From method doPost");
    }

    public abstract IDao<T> getDaoImpl() throws DaoException;
}
