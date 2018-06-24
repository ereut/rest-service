package ru.intervale.course.impl;

import org.apache.commons.codec.digest.DigestUtils;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;

import javax.annotation.Nullable;
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
    protected String getSelectQuery() {
        return "SELECT * FROM customers.customers";
    }

    @Override
    protected String getSelectAllQuery() {
        return getSelectQuery() + " WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM customers.customers WHERE id = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE customers.customers SET " +
                "password = IFNULL(?, `password`), " +
                "name = IFNULL(?, `name`), " +
                "surname = IFNULL(?, `surname`), " +
                "telephoneNumber = IFNULL(?, `telephoneNumber`), " +
                "address = IFNULL(?, `address`) " +
                "WHERE id = ?";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO customers.customers (login, password, name, surname, telephoneNumber, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, Customer entity)
            throws DaoException {

        try {
            pst.setString(1, DigestUtils.md2Hex(entity.getPassword()));
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
            pst.setString(2, DigestUtils.md2Hex(entity.getPassword()));
            pst.setString(3, entity.getName());
            pst.setString(4, entity.getSurname());
            pst.setString(5, entity.getTelephoneNumber());
            pst.setString(6, entity.getAddress());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement pst, Customer entity)
            throws DaoException {
        try {
            pst.setInt(1, entity.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    protected void prepareStatementForGetAll(PreparedStatement pst, Integer customerId)
            throws DaoException {
        try {
            pst.setInt(1, customerId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected List<Customer> parseResultSet(ResultSet rs) throws DaoException {
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

    @Nullable
    public Customer getCustomerByLoginAndPassword(String login, String password) throws DaoException {
        String sql = getSelectQuery() + " WHERE login = ? AND password = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, login);
            pst.setString(2, DigestUtils.md2Hex(password));
            ResultSet rs = pst.executeQuery();
            return !(rs.isBeforeFirst()) ? null : parseResultSet(rs).iterator().next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}