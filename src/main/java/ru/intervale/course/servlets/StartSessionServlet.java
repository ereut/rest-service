package ru.intervale.course.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;
import ru.intervale.course.servlets.enums.CustomerFields;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session/start")
public class StartSessionServlet extends HttpServlet {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter pw = resp.getWriter();

        try {

            CustomerJDBCDaoImpl customerIDaoImpl =
                    (CustomerJDBCDaoImpl) DaoFactory.getCustomerDaoImplFromFactory();
            RequestParameters requestParameters = new RequestParameters(req);

            String login = requestParameters.getRequired(CustomerFields.LOGIN.getName());
            String password = requestParameters.getRequired(CustomerFields.PASSWORD.getName());
            Customer customer = customerIDaoImpl.getCustomerByLoginAndPassword(login, password);

            if (customer == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.NOT_FOUND)));
                return;
            }

            Integer customerId = customer.getId();

            if (!CustomerSessionMap.isCurrentSessionValid(customerId)) {
                String newSessionId = RandomStringUtils.random(32, true, true).toUpperCase();
                CustomerSessionMap.insertSession(customerId, newSessionId);
                pw.print(objectMapper.writeValueAsString(CustomerSessionMap.getSession(customerId)));
            } else {
                CustomerSessionMap.updateSession(customerId);
                pw.print(objectMapper.writeValueAsString(CustomerSessionMap.getSession(customerId)));
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.flush();

        } catch (DaoException e) {
            pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.SERVER_ERROR)));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (InvalidRequestException e) {
           pw.print(objectMapper.writeValueAsString(new Error(Error.Errors.INVALID_REQUEST_PARAMETERS)));
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
