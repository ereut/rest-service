package ru.intervale.course.dao;

import ru.intervale.course.beans.Card;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class CardDao extends AbstractDao<Card> {

    public CardDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM cards";
    }

    @Override
    public List<Card> parseResultSet(ResultSet rs) throws DaoException {
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

    @Override
    public Card update(Card entity) {
        return null;
    }
}
