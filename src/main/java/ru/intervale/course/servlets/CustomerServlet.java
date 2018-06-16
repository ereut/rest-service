package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.DaoFactory;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/customer/*")
public class CustomerServlet extends AbstractEntityServlet<Customer, CustomerJDBCDaoImpl> {

    private enum CustomerFields {
        ID, LOGIN, PASSWORD, NAME, SURNAME, TELEPHONENUMBER, ADDRESS;

        public String getName() {
            return name().toLowerCase();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public IDao<Customer> getDaoImpl() throws DaoException {
        return DaoFactory.getCustomerDaoImplFromFactory();
    }

    @Override
    protected Customer parseReqBody(HttpServletRequest req) {

        Customer customer = new Customer();
        RequestParameters requestParameters = new RequestParameters(req);

        String login = requestParameters.getRequired(CustomerFields.LOGIN.getName());
        String password = requestParameters.getRequired(CustomerFields.PASSWORD.getName());



        return customer;
    }

}
