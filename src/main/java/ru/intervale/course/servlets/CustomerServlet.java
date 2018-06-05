package ru.intervale.course.servlets;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.CustomerJDBCDao;
import ru.intervale.course.dao.IDao;

import javax.servlet.annotation.WebServlet;

@WebServlet("/customer/*")
public class CustomerServlet extends AbstractServlet<Customer, CustomerJDBCDao> {

    private enum CustomersFields {
        ID, NAME, SURNAME, TELEPHONENUMBER, COUNTRY, CITY, STREET, HOMENUMBER, FLATNUMBER;
    }

    @Override
    protected IDao<Customer> getDaoImpl() {
        return new CustomerJDBCDao(getConnection());
    }

    @Override
    protected Customer parseAddReqBody(String body) {
        Customer customer = new Customer();
        for (String str : body.split("/t")) {
            CustomersFields field = CustomersFields.valueOf(getKeyFromLine(str).toUpperCase());
            switch (field) {
                case NAME:
                    customer.setName(getValueFromLine(str));
                    break;
                case SURNAME:
                    customer.setSurname(getValueFromLine(str));
                    break;
                case TELEPHONENUMBER:
                    customer.setTelephoneNumber(getValueFromLine(str));
                    break;
                case COUNTRY:
                    customer.getAddress().setCountry(getValueFromLine(str));
                    break;
                case CITY:
                    customer.getAddress().setCity(getValueFromLine(str));
                    break;
                case STREET:
                    customer.getAddress().setStreet(getValueFromLine(str));
                    break;
                case HOMENUMBER:
                    customer.getAddress().setHomeNumber(getValueFromLine(str));
                    break;
                case FLATNUMBER:
                    customer.getAddress().setFlatNumber(getValueFromLine(str));
                    break;
            }
        }
        return customer;
    }

    @Override
    protected Customer parseUpdateReqBody(String body) {
        return null;
    }


}
