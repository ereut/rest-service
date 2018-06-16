package ru.intervale.course.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.intervale.course.Constants;
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

public abstract class AbstractEntityServlet<T extends AbstractEntity, K extends AbstractJDBCDaoImpl<T>>
        extends HttpServlet {

    private enum Operations {
        ADD, DELETE, UPDATE;
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String RESPONSE_CHARACTER_ENCODING = "UTF-8";
    private static final String RESPONSE_CONTENT_TYPE = "application/json";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter pw = getPrintWriterFromResp(resp);
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || Constants.URI_DELIMITER.equals(pathInfo)) {
                pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.SUCCESS ));
                pw.print(MAPPER.writeValueAsString(getDaoImpl().getAll()));
                pw.flush();
                return;
            } else {
                try {
                    int id = Integer.parseInt(pathInfo.replace(Constants.URI_DELIMITER,""));
                    T entity = getDaoImpl().getEntityById(id);
                    if (entity == null) {
                        pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.NOT_FOUND));
                        pw.flush();
                        return;
                    }
                    pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.SUCCESS));
                    pw.print(MAPPER.writeValueAsString(entity));
                    pw.flush();
                } catch (NumberFormatException e) {
                    pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.BAD_REQUEST));
                    pw.flush();
                }
            }
        } catch (DaoException e) {
            pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.BAD_REQUEST));
            pw.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter pw = getPrintWriterFromResp(resp);
        String pathInfo = req.getPathInfo().replace(Constants.URI_DELIMITER, "");

        try {

            Operations operation = Operations.valueOf(pathInfo.toUpperCase());

            switch (operation) {

                case ADD:
                    try {
                        T entity = getDaoImpl().persist(parseReqBody(req));
                        pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.SUCCESS));
                        pw.print( MAPPER.writeValueAsString(entity));
                        pw.flush();
                    } catch (DaoException e) {
                        pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.BAD_REQUEST));
                        pw.flush();
                    }
                    return;

                case UPDATE:
                    try {
                        boolean isUpdate = getDaoImpl().update(parseReqBody(req));
                        printUpdateOrDeleteResult(isUpdate, pw);
                    } catch (DaoException e) {
                        pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.BAD_REQUEST));
                        pw.flush();
                    }
                    return;

                case DELETE:
                    try {
                        T entity = parseReqBody(req);
                        int id = entity.getId();
                        boolean isDeleted = getDaoImpl().delete(id);
                        printUpdateOrDeleteResult(isDeleted, pw);
                    } catch (DaoException e) {
                        pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.BAD_REQUEST));
                        pw.flush();
                    }
            }

        } catch (EnumConstantNotPresentException e) {
            pw.print(ResponceStatusCodes.BAD_REQUEST);
            pw.flush();
        }

    }

    protected abstract IDao<T> getDaoImpl() throws DaoException;
    protected abstract T parseReqBody(HttpServletRequest req);

    private static PrintWriter getPrintWriterFromResp(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(RESPONSE_CHARACTER_ENCODING);
        resp.setContentType(RESPONSE_CONTENT_TYPE);
        return resp.getWriter();
    }

    private static void printUpdateOrDeleteResult(boolean isDone, PrintWriter pw) throws IOException {
        if (isDone) {
            pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.SUCCESS));
            pw.flush();
        } else {
            pw.print(MAPPER.writeValueAsString(ResponceStatusCodes.NOT_FOUND));
            pw.flush();
        }
    }

}

