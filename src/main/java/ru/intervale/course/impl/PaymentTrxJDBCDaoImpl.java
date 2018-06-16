package ru.intervale.course.impl;

import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTrxJDBCDaoImpl extends AbstractJDBCDaoImpl<PaymentTrx> {

    public PaymentTrxJDBCDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM customers.payments";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM customers.payments WHERE id = ?";
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO customers.payments (cardId, startTime, finishTime, value, currency) " +
                "VALUES (?, ?, NOW(), ?, ?)";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
       throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
        try {
            pst.setInt(1, entity.getCardId());
            pst.setTimestamp(2, new java.sql.Timestamp(entity.getStartTrxTime().getTime()));
            pst.setInt(3, entity.getValue());
            pst.setString(4, entity.getMoneyCurrency().getName());
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<PaymentTrx> parseResultSet(ResultSet rs) throws DaoException {

        try {
            List<PaymentTrx> paymentsList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int cardId = rs.getInt(2);
                Date startTime = new Date(rs.getTimestamp(3).getTime());
                Date finishTime = new Date(rs.getTimestamp(4).getTime());
                int value = rs.getInt(5);
                String currency = rs.getString(6);
                PaymentTrx paymentTrx = new PaymentTrx(id, cardId, startTime, finishTime,
                        value, currency);
                paymentsList.add(paymentTrx);
            }
            return paymentsList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}