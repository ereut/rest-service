package ru.intervale.course.dao;

import ru.intervale.course.beans.PaymentTrx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTrxJDBCDao extends AbstractJDBCDao<PaymentTrx> {

    public PaymentTrxJDBCDao(Connection connection) {
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
        return "UPDATE customers.payments SET cardId = ?, value = ?, currency = ? WHERE id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO customers.payments (cardId, startTime, finishTime, value, currency) " +
                "VALUES (?, ?, CURRENT_TIME(), ?, ?)";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
        try {
            pst.setInt(1, entity.getCardId());
            pst.setInt(1, entity.getValue());
            pst.setString(3, entity.getMoneyCurrency().getName());
            pst.setInt(4, entity.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
        try {
            pst.setInt(1, entity.getCardId());
            pst.setTime(2, entity.getStartTrxTime());
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
                Time startTime = rs.getTime(3);
                Time finishTime = rs.getTime(4);
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
