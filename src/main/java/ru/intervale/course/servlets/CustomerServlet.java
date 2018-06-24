package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/*")
public class CustomerServlet extends AbstractEntityServlet<Customer, CustomerJDBCDaoImpl> {

    @Override
    protected Customer parseReqBody(HttpServletRequest req) throws InvalidRequestException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected IDao<Customer> getDaoImpl() throws DaoException {
        return DaoFactory.getCustomerDaoImplFromFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        getEntity(req, resp);

    }



}
