package ru.intervale.course.impl;

import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerJDBCDaoImpl extends AbstractJDBCDaoImpl<Customer> {

    public CustomerJDBCDaoImpl(Connection connection) {
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
        return "UPDATE customers.customers SET " +
                "password = IFNULL(?, `password`), " +
                "name = IFNULL(?, `name`), " +
                "surname = IFNULL(?, `surname`), " +
                "telephoneNumber = IFNULL(?, `telephoneNumber`), " +
                "address = IFNULL(?, `address`) " +
                "WHERE id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO customers.customers (login, password, name, surname, telephoneNumber, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement pst, Customer entity)
            throws DaoException {

        try {
            pst.setString(1, entity.getPassword());
            pst.setString(2, entity.getName());
            pst.setString(3, entity.getSurname());
            pst.setString(4, entity.getTelephoneNumber());
            pst.setString(5, entity.getAddress());
            pst.setInt(6, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, Customer entity)
            throws DaoException {
        try {
            pst.setString(1, entity.getLogin());
            pst.setString(2, entity.getPassword());
            pst.setString(3, entity.getName());
            pst.setString(4, entity.getSurname());
            pst.setString(5, entity.getTelephoneNumber());
            pst.setString(6, entity.getAddress());
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
                String login = rs.getString(2);
                String password = rs.getString(3);
                String name = rs.getString(4);
                String surname = rs.getString(5);
                String telephoneNumber = rs.getString(6);
                String address = rs.getString(7);

                Customer customer = new Customer(id, login, password, name, surname,
                        telephoneNumber, address);
                customersList.add(customer);
            }
            return customersList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}