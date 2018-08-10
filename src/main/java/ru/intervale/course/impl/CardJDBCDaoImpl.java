package ru.intervale.course.impl;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.DaoException;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CardJDBCDaoImpl extends AbstractJDBCDaoImpl<Card> {

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM customers.cards";
    }

    @Override
    protected String getSelectAllQuery() {
        return getSelectQuery() + " WHERE customerId = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM customers.cards WHERE id = ? AND customerId = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE customers.cards SET " +
                "pan = IFNULL(?, `pan`), " +
                "expiry = IFNULL(?, `expiry`), " +
                "title = IFNULL(?, `title`) " +
                "WHERE id = ? AND customerId = ?";
    }

    @Override
   protected String getCreateQuery() {
        return "INSERT INTO customers.cards (customerId, pan, expiry, registerTime, title) VALUES " +
                "(?, ?, ?, NOW(), ?)";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, Card entity)
            throws DaoException {

        try {
            pst.setString(1, entity.getPanCard());
            Date expiryCardDate = entity.getExpiryCardDate();
            pst.setString(2, expiryCardDate == null ? null :
                    Constants.CARD_EXPIRY_DATE_FORMAT.format(entity.getExpiryCardDate()));
            pst.setString(3, entity.getTitle());
            pst.setInt(4, entity.getId());
            pst.setInt(5, entity.getCustomerId());

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
    protected void prepareStatementForDelete(PreparedStatement pst, Card entity)
            throws DaoException {

        try {
            pst.setInt(1, entity.getId());
            pst.setInt(2,entity.getCustomerId());

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

    @Nonnull
    @Override
    protected List<Card> parseResultSet(ResultSet rs) throws DaoException {

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