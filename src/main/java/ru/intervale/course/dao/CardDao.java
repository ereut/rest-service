package ru.intervale.course.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.Constants;
import ru.intervale.course.beans.Card;


import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class CardDao extends AbstractDao<Card> {

    private static final Logger log = LoggerFactory.getLogger(CardDao.class);
    private static final String CREATE_CARD_QUERY =
            "INSERT INTO cards (customerId, pan, expiry) VALUES (?, ?, ?)";

    public CardDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM cards";
    }

    @Override
    public List<Card> parseResultSet(ResultSet rs) throws DaoException {

        try {
            List<Card> cardsList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int customerId = rs.getInt(2);
                String pan = rs.getString(3);
                Time time = rs.getTime(4);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }

        return null;
    }

    @Override
    public boolean delete(Card entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean create(Card entity) {
        return false;
    }

    public boolean create(int customerId, String pan, String expiry) throws DaoException {
        boolean flag = false;
        try (PreparedStatement pst = connection.prepareStatement(CREATE_CARD_QUERY)) {
            connection.setAutoCommit(false);
            CustomerDao customerDao = new CustomerDao(connection);

            if (customerDao.getEntityById(customerId) == null) {
                log.info("Card with pan {} is not registed", pan);
                return flag;
            }

            java.util.Date date = Constants.CARD_EXPIRY_DATE_FORMAT.parse(expiry);
            pst.setInt(1, customerId);
            pst.setString(2, pan);
            pst.setDate(3, new Date(date.getTime() + 24 * 60 * 60 * 1000));
            pst.executeUpdate();
            connection.commit();
            flag = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ParseException e) {
            log.error("Illegal input format of expiry card date {}", expiry);
        }
        return flag;
    }

    @Override
    public Card update(Card entity) {
        return null;
    }
}