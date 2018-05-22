package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.CustomerJDBCDao;
import ru.intervale.course.dao.IDao;

import javax.servlet.annotation.WebServlet;


@WebServlet("/customer/*")
public class CustomerServlet extends AbstractServlet<Customer, CustomerJDBCDao> {

    @Override
    protected IDao<Customer> getDaoImpl() {
        return new CustomerJDBCDao(getConnection());
    }

}
