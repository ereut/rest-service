package ru.intervale.course.dao.interfaces;


import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.DaoException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface IDao<T extends AbstractEntity> extends Serializable {

    List<T> getAll() throws DaoException;
    T getEntityById(int id) throws DaoException;
    boolean delete(int id) throws DaoException;


}
