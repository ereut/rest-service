package ru.intervale.course.dao;


import ru.intervale.course.beans.AbstractEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface IDao<T extends AbstractEntity> extends Serializable {

    List<T> getAll() throws DaoException;
    T getEntityById(int id) throws DaoException;
    boolean delete(T entity);
    boolean delete(int id);
    boolean create(T entity);
    T update(T entity);

}
