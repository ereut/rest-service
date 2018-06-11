package ru.intervale.course.impl;

import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractJDBCDaoImpl<T extends AbstractEntity> implements IDao<T> {

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
            LoggerFactory.getLogger(AbstractJDBCDaoImpl.class).error("Entity with id {} was not found", id);
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
        String sql = getUpdateQuery();
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(pst, entity);
            int count = pst.executeUpdate();
            if (count == 1) {
                return true;
            } else {
                throw new DaoException("Update more or less that one record " + count);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T persist (T entity) throws DaoException {
        if (entity.getId() != 0) {
            throw new DaoException("Entity is already persist");
        }
        String sql = getCreateQuery();
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            prepareStatementForInsert(pst, entity);
            int count = pst.executeUpdate();
            if (count != 1) {
                throw new DaoException("On persist modify more then 1 records " + count);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        sql = getSelectQuery() + " WHERE id = last_insert_id()";

        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            List<T> list = parseResultSet(rs);
            if (list.isEmpty() || list.size() != 1) {
                throw new DaoException("Insert error");
            }
            return list.iterator().next();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

}