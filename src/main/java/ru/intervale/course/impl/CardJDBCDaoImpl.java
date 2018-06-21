package ru.intervale.course.impl;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CardJDBCDaoImpl extends AbstractJDBCDaoImpl<Card> {

    public CardJDBCDaoImpl(Connection connection) {
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
        return "UPDATE customers.cards SET " +
                "customerId = IFNULL(?, `customerId`), " +
                "pan = IFNULL(?, `pan`), " +
                "expiry = IFNULL(?, `expiry`), " +
                "title = IFNULL(?, `title`) " +
                "WHERE id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO customers.cards (customerId, pan, expiry, registerTime, title) VALUES " +
                "(?, ?, ?, NOW(), ?)";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, Card entity)
            throws DaoException {
        try {
            Integer customerId = entity.getCustomerId();
            pst.setString(1, customerId == null ? null : String.valueOf(customerId));
            pst.setString(2, entity.getPanCard());
            Date expiryCardDate = entity.getExpiryCardDate();
            pst.setString(3, expiryCardDate == null ? null :
                    Constants.CARD_EXPIRY_DATE_FORMAT.format(entity.getExpiryCardDate()));
            pst.setString(4, entity.getTitle());
            pst.setInt(5, entity.getId());
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
            pst.setString(3, Constants.CARD_EXPIRY_DATE_FORMAT.format(entity.getExpiryCardDate()));
            pst.setString(4, entity.getTitle());
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
                Date date = rs.getTimestamp(5);
                String title = rs.getString(6);
                Card card = new Card(id, customerId, pan, expiry, date, title);
                cardsList.add(card);
            }
            return cardsList;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

}