package ru.intervale.course.dao.interfaces;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;

public interface ICustomerDao extends IDao<Customer> {

    boolean create(String name, String surname, String telephoneNumber,
                   String country, String city, String street, String homeNumber,
                   String flatNumber) throws DaoException;

    boolean update(int id, String name, String surname, String telephoneNumber,
                   String country, String city, String street, String homeNumber,
                   String flatNumber) throws DaoException;
}
