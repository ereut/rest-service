package ru.intervale.course.dao;

import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.interfaces.ICustomerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerJDBCDao extends AbstractJDBCDao<Customer> implements ICustomerDao {

    private static final String CREATE_CUSTOMER_QUERY =
            "INSERT INTO customers.customers (name, surname, telephoneNumber, country, city, " +
            "street, homeNumber, flatNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CUSTOMER_QUERY =
            "UPDATE customers.customers SET name = ?, surname = ?, telephoneNumber = ?, country = ?," +
            "city = ?, street = ?, homeNumber = ?, flatNumber = ? WHERE id = ?";

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
    public boolean create (String name, String surname, String telephoneNumber,
                           String country, String city, String street, String homeNumber,
                           String flatNumber) throws DaoException {
        try (PreparedStatement pst = connection.prepareStatement(CREATE_CUSTOMER_QUERY)) {
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, telephoneNumber);
            pst.setString(4, country);
            pst.setString(5, city);
            pst.setString(6, street);
            pst.setString(7, homeNumber);
            pst.setString(8, flatNumber);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(int id, String name, String surname, String telephoneNumber,
                          String country, String city, String street, String homeNumber,
                          String flatNumber) throws DaoException {
        if (getEntityById(id) == null) {
            LoggerFactory.getLogger(CustomerJDBCDao.class).error("Customer with id {} not found", id);
            return false;
        }
        try (PreparedStatement pst = connection.prepareStatement(UPDATE_CUSTOMER_QUERY)) {
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, telephoneNumber);
            pst.setString(4, country);
            pst.setString(5, city);
            pst.setString(6, street);
            pst.setString(7, homeNumber);
            pst.setString(8, flatNumber);
            pst.setInt(9, id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }
}
