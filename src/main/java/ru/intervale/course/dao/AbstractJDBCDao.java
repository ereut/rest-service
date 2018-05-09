package ru.intervale.course.dao;

import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.interfaces.IDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractJDBCDao<T extends AbstractEntity> implements IDao<T> {

    protected Connection connection;

    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }

    public abstract String getSelectQuery();
    public abstract String getDeleteQuery();
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
            LoggerFactory.getLogger(AbstractJDBCDao.class).error("Entity with id {} was not found", id);
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

}
