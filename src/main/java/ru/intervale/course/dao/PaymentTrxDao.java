package ru.intervale.course.dao;

import ru.intervale.course.beans.PaymentTrx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class PaymentTrxDao extends AbstractDao<PaymentTrx> {

    public PaymentTrxDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM payments";
    }

    @Override
    public List<PaymentTrx> parseResultSet(ResultSet rs) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(PaymentTrx entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean create(PaymentTrx entity) {
        return false;
    }

    @Override
    public PaymentTrx update(PaymentTrx entity) {
        return null;
    }
}
