package ru.intervale.course.impl;

import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;

import java.sql.*;
import java.util.List;

public abstract class AbstractJDBCDaoImpl<T extends AbstractEntity> implements IDao<T> {

    private static final String ENTITY_ALREADY_PERSIST_MESSAGE = "Entity is already persist";
    private static final String INVALID_PERSIST_MODIFY_MESSAGE = "On persist modify more then 1 records ";
    private static final String INVALID_INSERT_MESSAGE = "Entity wasn't insert";
    private static final String INVALID_UPDATE_MESSAGE = "Update more or less that one record ";
    private static final String INVALID_ENTITY_FOR_UPDATE = "Entity without id didn't save";
    private static final String INVALID_DELETE_LOGGER_MESSAGE = "Entity with id {} was not found";

    protected Connection connection;

    public AbstractJDBCDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public abstract String getSelectQuery();
    public abstract String getDeleteQuery();
    public abstract String getUpdateQuery();
    public abstract String getCreateQuery();
    protected abstract void prepareStatementForUpdate(PreparedStatement pst, T entity)
            throws DaoException;
    protected abstract void prepareStatementForInsert(PreparedStatement pst, T entity)
            throws DaoException;
    protected abstract List<T> parseResultSet(ResultSet rs) throws DaoException;

    public String getSelectByIdQuery() {
        return getSelectQuery() + " WHERE id = ?";
    }

    @Override
    public T getEntityById(int id) throws DaoException {
        try (PreparedStatement pst =
                     connection.prepareStatement(getSelectByIdQuery())) {
            List<T> list;
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            list = parseResultSet(rs);
            if (list.isEmpty()) {
                return null;
            }
            return list.iterator().next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        try (PreparedStatement pst = connection.prepareStatement(getSelectQuery())) {
            List<T> list;
            ResultSet rs = pst.executeQuery();
            list = parseResultSet(rs);
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        if (getEntityById(id) == null) {
            LoggerFactory.getLogger(AbstractJDBCDaoImpl.class).error(INVALID_DELETE_LOGGER_MESSAGE, id);
            return false;
        }
        try (PreparedStatement pst = connection.prepareStatement(getDeleteQuery())) {
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(T entity) throws DaoException {
        if (entity.getId() == 0) {
            throw new DaoException(INVALID_ENTITY_FOR_UPDATE);
        }
        String sql = getUpdateQuery();
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(pst, entity);
            int count = pst.executeUpdate();
            if (count == 1) {
                return true;
            } else {
                throw new DaoException(INVALID_UPDATE_MESSAGE + count);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T persist (T entity) throws DaoException {
        if (entity.getId() != 0) {
            throw new DaoException(ENTITY_ALREADY_PERSIST_MESSAGE);
        }
        String sql = getCreateQuery();
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(pst, entity);
            int count = pst.executeUpdate();
            if (count != 1) {
                throw new DaoException(INVALID_PERSIST_MODIFY_MESSAGE + count);
            }
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                    return entity;
                } else {
                    throw new DaoException(INVALID_INSERT_MESSAGE);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
