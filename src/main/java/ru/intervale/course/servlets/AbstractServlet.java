package ru.intervale.course.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.AbstractJDBCDao;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.dao.JDBCConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


public abstract class AbstractServlet<T extends AbstractEntity, E extends AbstractJDBCDao<T>>
        extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(AbstractServlet.class);
    private Connection connection;
    private ObjectMapper mapper = new ObjectMapper();
    Operations operation;
    IDao<T> iDao;

    @Override
    public void init() throws ServletException {
        try {
            connection = JDBCConnector.getConnection();
            iDao = getDaoImpl();
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
             operation = getOperationFromRequest(req);
        } catch (IllegalUriFormatException e) {
            logger.error(e.getMessage());
            return;
        }

        if (operation != Operations.GET) {
            logger.error("Illegal operation for method GET: {} ", operation.toString().toLowerCase());
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String parameterId = req.getParameter("id");
        PrintWriter pw = resp.getWriter();

        if (Strings.isNullOrEmpty(parameterId)) {
            try {

                pw.print(StatusCodes.SUCCESS);
                pw.print(mapper.writeValueAsString(iDao.getAll()));
                pw.flush();
            } catch (DaoException e) {
                logger.error(e.getMessage());
            }
        } else {
            try {
                int id = Integer.valueOf(req.getParameter("id"));
                T entity = iDao.getEntityById(id);
                pw.print(StatusCodes.SUCCESS);
                pw.print(mapper.writeValueAsString(entity));
                pw.flush();
            } catch (DaoException e) {
                pw.print(StatusCodes.NOT_FOUND);
                logger.error(e.getMessage());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            operation = getOperationFromRequest(req);
        } catch (IllegalUriFormatException e) {
            logger.error(e.getMessage());
            return;
        }
        if (operation == Operations.GET) {
            logger.error("Illegal operation for method POST: {} ", operation.toString().toLowerCase());
            return;
        }

        String reqBody = IOUtils.toString(req.getReader());


        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        switch (operation) {
           case ADD:
               try {
                   T entity = iDao.persist(parseAddReqBody(reqBody));
                   pw.print( mapper.writeValueAsString(entity));
                   pw.flush();
                   return;
               } catch (DaoException e) {

               }
           case UPDATE:
               break;
           case DELETE:
               break;


       }
    }

    @Override
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        iDao = null;
    }

    public Connection getConnection() {
        return connection;
    }

    private Operations getOperationFromRequest(HttpServletRequest req)
          throws IllegalUriFormatException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        if (uriParts.length == 4 && !Strings.isNullOrEmpty(uriParts[3])) {
            String fullOperation = uriParts[3];
            int parameter = fullOperation.indexOf("?");
            String operation = parameter == -1 ? fullOperation : fullOperation.substring(0, parameter);
            try {
                return Operations.valueOf(operation.toUpperCase());
            } catch (EnumConstantNotPresentException e){
                throw new IllegalUriFormatException("Uri contains invalid command " + uri, e);
            }
        } else {
            throw new IllegalUriFormatException("Invalid Uri form: " + uri);
        }
    }

    public static String getKeyFromLine(String line) {
        return line.substring(0, line.indexOf("="));
    }

    public static String getValueFromLine(String line) {
        return line.substring(line.indexOf("=") + 1);
    }

    protected abstract IDao<T> getDaoImpl();
    protected abstract T parseAddReqBody(String body);
    protected abstract T parseUpdateReqBody(String body);

}