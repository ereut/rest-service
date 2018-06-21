package ru.intervale.course.servlets;

import ru.intervale.course.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FinishSessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String sessionId = ServletUtils.parseSessionHeader(req);
        Integer customerId = CustomerSessionMap.getCustomerId(sessionId);

        if (customerId == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        CustomerSessionMap.invalidateSession(customerId);
        resp.setStatus(HttpServletResponse.SC_OK);

    }

}
