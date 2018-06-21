package ru.intervale.course.impl;

import ru.intervale.course.Constants;
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
        return "INSERT INTO customers.payments (cardId, startTime, finishTime, value, currency, expiry, pan) " +
                "VALUES (?, ?, NOW(), ?, ?, ?, ?)";
    }

    private static final String GET_CARDS_ID_QUERY = "SELECT id FROM customers.cards WHERE customerId = ?";

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
       throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
        try {
            Integer cardId = entity.getCardId();
            pst.setString(1, cardId == null ? null : String.valueOf(cardId));
            pst.setTimestamp(2, new java.sql.Timestamp(entity.getStartTrxTime().getTime()));
            pst.setInt(3, entity.getValue());
            pst.setString(4, entity.getMoneyCurrency().getName());
            java.util.Date expiry = entity.getExpiry();
            pst.setString(5, expiry == null ? null : Constants.CARD_EXPIRY_DATE_FORMAT.format(expiry));
            String pan = entity.getPan();
            pst.setString(6, pan == null ? null : pan);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<PaymentTrx> parseResultSet(ResultSet rs) throws DaoException {

        try {
            List<PaymentTrx> paymentsList = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String strCardId = rs.getString(2);
                Integer cardId = strCardId == null ? null : Integer.valueOf(strCardId) ;
                Date startTime = new Date(rs.getTimestamp(3).getTime());
                Date finishTime = new Date(rs.getTimestamp(4).getTime());
                int value = rs.getInt(5);
                String currency = rs.getString(6);
                String expiry = rs.getString(7);
                String pan = rs.getString(8);
                PaymentTrx paymentTrx = new PaymentTrx(id, cardId, startTime, finishTime,
                        value, currency, expiry, pan);
                paymentsList.add(paymentTrx);
            }
            return paymentsList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean isCardBelongsCustomer(int customerId, int cardId) throws DaoException {
        try (PreparedStatement pst = connection.prepareStatement(GET_CARDS_ID_QUERY)) {
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                if (cardId == rs.getInt(1)) {
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}