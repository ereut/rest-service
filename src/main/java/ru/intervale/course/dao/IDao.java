package ru.intervale.course.dao;


import ru.intervale.course.beans.AbstractEntity;

import java.io.Serializable;
import java.util.List;

public interface IDao<T extends AbstractEntity> extends Serializable {

    List<T> getAll() throws DaoException;
    T getEntityById(int id) throws DaoException;
    boolean delete(int id) throws DaoException;
    boolean update(T entity) throws DaoException;
    T persist(T entity) throws DaoException;

}

