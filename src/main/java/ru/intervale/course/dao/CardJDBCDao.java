package ru.intervale.course.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.Card;
import ru.intervale.course.dao.interfaces.ICardDao;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class CardJDBCDao extends AbstractJDBCDao<Card> implements ICardDao {

    private static final Logger log = LoggerFactory.getLogger(CardJDBCDao.class);
    private static final String CREATE_CARD_QUERY =
            "INSERT INTO customers.cards (customerId, pan, expiry, registerTime) VALUES " +
                    "(?, ?, ?, CURRENT_TIME())";
    private static final String UPDATE_CARD_QUERY =
            "UPDATE customers.cards SET customerId = ?, pan = ?, expiry = ? WHERE id = ?";

    private static boolean isExpiryCardDateInvalid(String date) {
        final SimpleDateFormat CARD_EXPIRY_DATE_FORMAT =
                new SimpleDateFormat("MMyy");
        CARD_EXPIRY_DATE_FORMAT.setLenient(true);
        try {
            return !CARD_EXPIRY_DATE_FORMAT.format(CARD_EXPIRY_DATE_FORMAT.parse(date)).equals(date);
        } catch (ParseException e) {
            return true;
        }
    }

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

    public boolean create(int customerId, String pan, String expiry) throws DaoException {

        if (new CustomerJDBCDao(connection).getEntityById(customerId) == null) {
            log.error("No search any customer with id {}. " +
                    "Card with pan {} is not registed", customerId, pan);
            return false;
        }
        if (isExpiryCardDateInvalid(expiry)) {
            log.error("Illegal date or input format of expiry card date {}", expiry);
            return false;
        }
        try (PreparedStatement pst = connection.prepareStatement(CREATE_CARD_QUERY)) {
            pst.setInt(1, customerId);
            pst.setString(2, pan);
            pst.setString(3, expiry);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(int id, int customerId, String pan, String expiry)
            throws DaoException {
        if (getEntityById(id) == null) {
            log.error("Card with id {} was not found", id);
            return false;
        }
        if (new CustomerJDBCDao(connection).getEntityById(customerId) == null) {
            log.error("Customer with id {} was not found", customerId);
            return false;
        }
        if (isExpiryCardDateInvalid(expiry)) {
            log.error("Illegal date or input format of expiry card date {}", expiry);
            return false;
        }
        try (PreparedStatement pst =
                     connection.prepareStatement(UPDATE_CARD_QUERY)) {
            pst.setInt(1, customerId);
            pst.setString(2, pan);
            pst.setString(3, expiry);
            pst.setInt(4, id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}