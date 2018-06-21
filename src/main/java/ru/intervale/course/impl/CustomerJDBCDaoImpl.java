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

    private static final String INSERT_SESSION_QUERY = "UPDATE customers.customers SET" +
            " sessionId = ?, " +
            "sessionLifeTime = DATE_ADD(NOW(), INTERVAL 20 MINUTE) " +
            "WHERE id = ?";

    private static final String UPDATE_CURRENT_SESSION_QUERY = "UPDATE customers.customers SET " +
            "sessionLifeTime = DATE_ADD(NOW(), INTERVAL 20 MINUTE) " +
            "WHERE id = ?";

    private final static String GET_VALID_SESSION_ID_QUERY =
            "SELECT `sessionLifeTime` > NOW(), `sessionId` FROM customers.customers WHERE id = ?";

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

    public Customer getCustomerByLoginAndPassword(String login, String password) throws DaoException {
        String sql = getSelectQuery() + " WHERE login = ? AND password = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, login);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return parseResultSet(rs).iterator().next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean insertSession(Customer customer, String sessionId) throws DaoException {

        try (PreparedStatement pst = connection.prepareStatement(INSERT_SESSION_QUERY)) {
            pst.setString(1, sessionId);
            pst.setInt(2, customer.getId());
            return pst.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean updateCurrentSession(Customer customer) throws DaoException {
        try (PreparedStatement pst = connection.prepareStatement(UPDATE_CURRENT_SESSION_QUERY)) {
            pst.setInt(1, customer.getId());
            return pst.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public String getValidSessionId(Customer customer) throws DaoException {
        try (PreparedStatement pst = connection.prepareStatement(GET_VALID_SESSION_ID_QUERY)) {
            pst.setInt(1, customer.getId());
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1) == 1 ? rs.getString(2) : null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static final String SELECT_CUSTOMER_ID_QUERY = "SELECT `id` FROM customers.customers " +
            "WHERE `sessionId` = ? ";

    public int getCustomerIdBySessionId(String sessionId) throws DaoException {
        try (PreparedStatement pst =
                     connection.prepareStatement(SELECT_CUSTOMER_ID_QUERY)) {

            pst.setString(1, sessionId );
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

}