package ru.intervale.course.dao.interfaces;

import ru.intervale.course.dao.DaoException;

public interface IPaymentTrxDao {

    boolean create (int cardId, int value, String currency) throws DaoException;
    boolean update (int id,int cardId, int value, String currency) throws DaoException;

}
