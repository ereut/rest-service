package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/customer/*")
public class CustomerServlet extends AbstractServlet<Customer, CustomerJDBCDaoImpl> {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public IDao<Customer> getDaoImpl() throws DaoException {
        return DaoFactory.getCustomerDaoImplFromFactory();
    }
}
