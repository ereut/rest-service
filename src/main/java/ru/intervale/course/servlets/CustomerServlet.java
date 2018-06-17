package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;

import javax.servlet.ServletException;

public abstract class CustomerServlet extends AbstractEntityServlet<Customer, CustomerJDBCDaoImpl> {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected IDao<Customer> getDaoImpl() throws DaoException {
        return DaoFactory.getCustomerDaoImplFromFactory();
    }

}
