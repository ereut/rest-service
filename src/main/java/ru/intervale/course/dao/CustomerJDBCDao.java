package ru.intervale.course.dao;

import ru.intervale.course.beans.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerJDBCDao extends AbstractJDBCDao<Customer> {

    public CustomerJDBCDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM customers.customers";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM customers.customers WHERE id = ?";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE customers.customers SET name = ?, surname = ?, telephoneNumber = ?, country = ?," +
                "city = ?, street = ?, homeNumber = ?, flatNumber = ? WHERE id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO customers.customers (name, surname, telephoneNumber, country, city, " +
                "street, homeNumber, flatNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement pst, Customer entity)
            throws DaoException {
        try {
            pst.setString(1, entity.getName());
            pst.setString(2, entity.getSurname());
            pst.setString(3, entity.getTelephoneNumber());
            pst.setString(4, entity.getAddress().getCountry());
            pst.setString(5, entity.getAddress().getCity());
            pst.setString(6, entity.getAddress().getStreet());
            pst.setString(7, entity.getAddress().getHomeNumber());
            pst.setString(8, entity.getAddress().getFlatNumber());
            pst.setInt(9, entity.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, Customer entity)
            throws DaoException {
        try {
            pst.setString(1, entity.getName());
            pst.setString(2, entity.getSurname());
            pst.setString(3, entity.getTelephoneNumber());
            pst.setString(4, entity.getAddress().getCountry());
            pst.setString(5, entity.getAddress().getCity());
            pst.setString(6, entity.getAddress().getStreet());
            pst.setString(7, entity.getAddress().getHomeNumber());
            pst.setString(8, entity.getAddress().getFlatNumber());
        } catch (SQLException e) {
           throw new DaoException();
        }
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
                String country = rs.getString(5);
                String city = rs.getString(6);
                String street = rs.getString(7);
                String homeNumber = rs.getString(8);
                String flatNumber = rs.getString(9);
                Customer customer = new Customer(id, name, surname, telephoneNumber,
                        country, city, street, homeNumber,flatNumber);
                customersList.add(customer);
            }
            return customersList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
