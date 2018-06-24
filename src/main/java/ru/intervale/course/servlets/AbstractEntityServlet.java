package ru.intervale.course.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.intervale.course.Constants;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.AbstractJDBCDaoImpl;
import ru.intervale.course.impl.InvalidDataException;
import ru.intervale.course.utils.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractEntityServlet<T extends AbstractEntity, K extends AbstractJDBCDaoImpl<T>>
        extends HttpServlet {

    private static ObjectMapper objectMapper = new ObjectMapper();

    protected abstract IDao<T> getDaoImpl() throws DaoException;
    protected abstract T parseReqBody(HttpServletRequest req)
            throws InvalidRequestException;

    protected void deleteEntity(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        PrintWriter pw = ServletUtils.getPrintWriter(resp);
        try {
            boolean result = getDaoImpl().delete(parseReqBody(req));
            if (result) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.NOT_FOUND)));
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DaoException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.SERVER_ERROR)));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (InvalidRequestException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.INVALID_REQUEST_PARAMETERS)));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void getEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter pw = ServletUtils.getPrintWriter(resp);
        String pathInfo = req.getPathInfo();

        try {

            if (pathInfo == null || Constants.URI_DELIMITER.equals(pathInfo)) {

                String sessionId = ServletUtils.parseSessionHeader(req);
                Integer customerId = CustomerSessionMap.getCustomerId(sessionId);

                if (customerId == null) {
                    pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.UNAUTHORIZED_CUSTOMER)));
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                pw.print(objectMapper.writeValueAsString(getDaoImpl().getAllByCustomerId(customerId)));

            } else {
                int id = Integer.parseInt(pathInfo.replace(Constants.URI_DELIMITER, ""));
                AbstractEntity entity = getDaoImpl().getEntityById(id);
                if (entity == null) {
                    pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.NOT_FOUND)));
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                pw.print(objectMapper.writeValueAsString(entity));
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.flush();

        } catch (DaoException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.SERVER_ERROR)));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void updateEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter pw = ServletUtils.getPrintWriter(resp);

        try {
            boolean result = getDaoImpl().update(parseReqBody(req));
            if (result) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.NOT_FOUND)));
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (DaoException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.SERVER_ERROR)));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (InvalidRequestException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.BAD_REQUEST)));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    protected void addEntity(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        PrintWriter pw = ServletUtils.getPrintWriter(resp);

        try {
            AbstractEntity entity = getDaoImpl().persist(parseReqBody(req));
            pw.print(objectMapper.writeValueAsString(entity));
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.flush();

        } catch (DaoException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.SERVER_ERROR)));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (InvalidRequestException | InvalidDataException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.BAD_REQUEST)));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

}

