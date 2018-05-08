package ru.intervale.course.dao;


import ru.intervale.course.beans.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends AbstractDao<Customer> {

    public CustomerDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM customers";
    }

    @Override
    public List<Customer> parseResultSet(ResultSet rs) throws DaoException {
        try {
            List<Customer> customersList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                String telephoneNumber = rs.getString(4);
                Customer customer = new Customer(id, name, surname, telephoneNumber);
                String country = rs.getString(5);
                String city = rs.getString(6);
                String street = rs.getString(7);
                String homeNumber = rs.getString(8);
                String flatNumber = rs.getString(9);
                customer.setAddress(country, city, street,
                        homeNumber, flatNumber);
                customersList.add(customer);
            }
            return customersList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Customer entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean create(Customer entity) {
        return false;
    }

    @Override
    public Customer update(Customer entity) {
        return null;
    }
}
