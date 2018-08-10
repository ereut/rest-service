package ru.intervale.course.dao;


import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.impl.InvalidDataException;

import java.io.Serializable;
import java.util.List;

public interface IDao<T extends AbstractEntity> extends Serializable {

    List<T> getAll() throws DaoException;
    List<T> getAllByCustomerId(Integer customerId) throws DaoException;
    T getEntityById(Integer id) throws DaoException;
    boolean delete(T entity) throws DaoException;
    boolean update(T entity) throws DaoException;
    T persist(T entity) throws DaoException, InvalidDataException;

}

