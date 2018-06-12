package ru.intervale.course.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.AbstractJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractServlet<T extends AbstractEntity, K extends AbstractJDBCDaoImpl<T>> extends HttpServlet {

    private enum Operations {
        ADD, DELETE, UPDATE;
    }

    private static Logger logger = LoggerFactory.getLogger(AbstractServlet.class);
    private IDao<T> iDaoImpl;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String PROBLEMS_WITH_CREATION_DAO_IMPL_MESSAGE =
            "Problems with creation dao implementation";
    private static final String RESPONCE_CHARACTER_ENCODING = "UTF-8";
    private static final String RESPONCE_CONTENT_TYPE = "application/json";

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

    private static PrintWriter getPrintWriterFromResp(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(RESPONCE_CHARACTER_ENCODING);
        resp.setContentType(RESPONCE_CONTENT_TYPE);
        return resp.getWriter();
    }
}

