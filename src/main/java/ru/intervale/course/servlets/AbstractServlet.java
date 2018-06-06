package ru.intervale.course.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private Operations operation;
    private IDao<T> iDao;

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
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter pw = getWriterForResp(resp);

        if (getUriParts(req).length == 3) {

            try {
                resp.setStatus(HttpServletResponse.SC_OK);
                pw.print(StatusCodes.SUCCESS + "\n");
                pw.print(mapper.writeValueAsString(iDao.getAll()));
                pw.flush();
            } catch (DaoException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(e.getMessage());
            }

        } else if(getUriParts(req).length == 4) {

            try {
                int id = Integer.valueOf(getUriParts(req)[3]);
                T entity = iDao.getEntityById(id);
                if (entity == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    pw.print(StatusCodes.NOT_FOUND + "\n");
                    pw.print("Not found entity with id " + id);
                    pw.flush();
                    return;
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                pw.print(StatusCodes.SUCCESS + "\n");
                pw.print(mapper.writeValueAsString(entity));
                pw.flush();

            } catch (DaoException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(e.getMessage());
            }
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Illegal uri format: {}", req.getRequestURI());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter pw = getWriterForResp(resp);

        operation = Operations.valueOf(getUriParts(req)[3].toUpperCase());
        String reqBody = IOUtils.toString(req.getReader()).trim();

        switch (operation) {
           case ADD:
               try {
                   T entity = iDao.persist(parseReqBody(reqBody));
                   resp.setStatus(HttpServletResponse.SC_OK);
                   pw.print(StatusCodes.SUCCESS + "\n");
                   pw.print( mapper.writeValueAsString(entity));
                   pw.flush();
               } catch (DaoException e) {
                   resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                   logger.error(e.getMessage());
                   pw.print(StatusCodes.BAD_REQUEST + "\n");
                   pw.flush();
               }
               return;
           case UPDATE:
               try {
                   boolean isUpdate = iDao.update(parseReqBody(reqBody));
                   printUpdateOrDeleteResult(isUpdate, pw, resp);
               } catch (DaoException e) {
                   resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                   logger.error(e.getMessage());
                   pw.print(StatusCodes.BAD_REQUEST + "\n");
                   pw.flush();
               }
               return;
           case DELETE:
               try {
                   T entity = parseReqBody(reqBody);
                   int id = entity.getId();
                   boolean isDeleted = iDao.delete(id);
                   printUpdateOrDeleteResult(isDeleted, pw, resp);
               } catch (DaoException e) {
                   resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                   logger.error(e.getMessage());
                   pw.print(StatusCodes.BAD_REQUEST);
                   pw.flush();
               }
        }
    }

    private static void printUpdateOrDeleteResult(boolean isDone, PrintWriter pw,
                                                  HttpServletResponse resp) throws IOException {
        if (isDone) {
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.print(StatusCodes.SUCCESS);
            pw.flush();
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            pw.print(StatusCodes.NOT_FOUND + "\n");
            pw.print("Entity not found");
            pw.flush();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private static PrintWriter getWriterForResp(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        return resp.getWriter();
    }

    private static String[] getUriParts(HttpServletRequest req) {
        return req.getRequestURI().split("/");
    }

    public static String getKeyFromLine(String line) {
        return line.substring(0, line.indexOf("="));
    }

    public static String getValueFromLine(String line) {
        return line.substring(line.indexOf("=") + 1);
    }

    protected abstract IDao<T> getDaoImpl();
    protected abstract T parseReqBody(String body);

}