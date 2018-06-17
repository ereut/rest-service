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

    private static final String RESPONSE_CHARACTER_ENCODING = "UTF-8";
    private static final String RESPONSE_CONTENT_TYPE = "application/json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected abstract IDao<T> getDaoImpl() throws DaoException;
    protected abstract T parseReqBody(HttpServletRequest req);

    protected void deleteEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            getDaoImpl().delete(parseReqBody(req).getId());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DaoException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void getEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = getPrintWriter(resp);
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || Constants.URI_DELIMITER.equals(pathInfo)) {
                pw.print(objectMapper.writeValueAsString(getDaoImpl().getAll()));
            } else {
                int id = Integer.parseInt(pathInfo.replace(Constants.URI_DELIMITER, ""));
                AbstractEntity entity = getDaoImpl().getEntityById(id);
                if (entity == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                pw.print(objectMapper.writeValueAsString(entity));
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.flush();
        } catch (DaoException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void updateEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            getDaoImpl().update(parseReqBody(req));
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (DaoException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void addEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            PrintWriter pw = getPrintWriter(resp);
            AbstractEntity entity = getDaoImpl().persist(parseReqBody(req));
            pw.print(objectMapper.writeValueAsString(entity));
            pw.flush();
        } catch (DaoException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private static PrintWriter getPrintWriter(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(RESPONSE_CHARACTER_ENCODING);
        resp.setContentType(RESPONSE_CONTENT_TYPE);
        return resp.getWriter();
    }

}

