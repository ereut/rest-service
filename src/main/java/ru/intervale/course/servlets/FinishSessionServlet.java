package ru.intervale.course.servlets;

import ru.intervale.course.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/session/finish")
public class FinishSessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String sessionId = ServletUtils.parseSessionHeader(req);
        Integer customerId = CustomerSessionMap.getCustomerId(sessionId);

        CustomerSessionMap.invalidateSession(customerId);
        resp.setStatus(HttpServletResponse.SC_OK);

    }

}
