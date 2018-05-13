package ru.intervale.course.dao;

import ru.intervale.course.beans.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CardJDBCDao extends AbstractJDBCDao<Card> {

    public CardJDBCDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM customers.cards";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM customers.cards WHERE id = ?";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE customers.cards SET customerId = ?, pan = ?, expiry = ? WHERE id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO customers.cards (customerId, pan, expiry, registerTime) VALUES " +
                "(?, ?, ?, CURRENT_TIME())";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, Card entity)
            throws DaoException {
        try {
            pst.setInt(1, entity.getCustomerId());
            pst.setString(2, entity.getPanCard());
            pst.setString(3, entity.getExpiryCardDate());
            pst.setInt(4, entity.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, Card entity)
            throws DaoException {
        try {
            pst.setInt(1, entity.getCustomerId());
            pst.setString(2, entity.getPanCard());
            pst.setString(3, entity.getExpiryCardDate());
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Card> parseResultSet(ResultSet rs) throws DaoException {

        try {
            List<Card> cardsList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int customerId = rs.getInt(2);
                String pan = rs.getString(3);
                String expiry = rs.getString(4);
                String time = rs.getString(5);
                Card card = new Card(id, customerId, pan, expiry, time);
                cardsList.add(card);
            }
            return cardsList;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}