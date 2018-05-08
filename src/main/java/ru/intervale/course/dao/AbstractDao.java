package ru.intervale.course.dao;

import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao <T extends AbstractEntity> implements IDao<T> {

    protected Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract String getSelectQuery();
    public abstract List<T> parseResultSet(ResultSet rs) throws DaoException;

    @Override
    public T getEntityById(int id) throws DaoException {
        String sqlQuery = getSelectQuery() + " WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sqlQuery)) {
            List<T> list;
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            list = parseResultSet(rs);
            if (list.isEmpty()) {
                LoggerFactory.getLogger(AbstractDao.class).info("No search such entity with id = {}", id);
                return null;
            }
            return list.iterator().next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        String sqlQuery = getSelectQuery();
        try (PreparedStatement pst = connection.prepareStatement(sqlQuery)) {
            List<T> list;
            ResultSet rs = pst.executeQuery();
            list = parseResultSet(rs);
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
