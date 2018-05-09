package ru.intervale.course.dao.interfaces;

import ru.intervale.course.dao.DaoException;

public interface ICardDao {

    boolean create(int customerId, String pan, String expiry) throws DaoException;
    boolean update(int id, int customerId, String pan, String expiry) throws DaoException;

}
