package ru.intervale.course.impl;

import ru.intervale.course.Constants;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.JDBCConnector;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTrxJDBCDaoImpl extends AbstractJDBCDaoImpl<PaymentTrx> {

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM customers.payments";
    }

    @Override
    protected String getSelectAllQuery() {
        return getSelectQuery() + " WHERE customerId = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM customers.payments WHERE id = ?";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO customers.payments " +
                "(cardId, startTime, finishTime, value, currency, expiry, pan, customerId) " +
                "VALUES (?, ?, NOW(), ?, ?, ?, ?, ?)";
    }

    private static final String CHECKING_CARD_SQL_QUERY =
            "SELECT customerId FROM customers.cards WHERE id = ? AND customerId = ?";

    @Override
    protected String getUpdateQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
       throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement pst, PaymentTrx entity)
            throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareStatementForGetAll(PreparedStatement pst, Integer customerId)
            throws DaoException {

        try {
            pst.setInt(1, customerId);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement pst, PaymentTrx entity)
            throws DaoException, InvalidDataException {

        Integer customerId = entity.getCustomerId();
        Integer cardId = entity.getCardId();

        if (customerId != null) {

            try (Connection connection = JDBCConnector.getJDBCConnection();
                 PreparedStatement checkingPst = connection.prepareStatement(CHECKING_CARD_SQL_QUERY)) {

                checkingPst.setInt(1, cardId);
                checkingPst.setInt(2, customerId);
                ResultSet rs = checkingPst.executeQuery();

                if (!rs.isBeforeFirst()) {
                    throw new InvalidDataException("Invalid idCard: " + cardId +
                            "for customer with id " + customerId);
                }

            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        try {

            pst.setString(1, cardId == null ? null : String.valueOf(cardId));
            pst.setTimestamp(2, new java.sql.Timestamp(entity.getStartTrxTime().getTime()));
            pst.setInt(3, entity.getValue());
            pst.setString(4, entity.getMoneyCurrency().getName());
            java.util.Date expiry = entity.getExpiry();
            pst.setString(5, expiry == null ? null : Constants.CARD_EXPIRY_DATE_FORMAT.format(expiry));
            String pan = entity.getPan();
            pst.setString(6, pan);
            pst.setString(7, customerId == null ? null : String.valueOf(customerId));

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Nonnull
    @Override
    protected List<PaymentTrx> parseResultSet(ResultSet rs) throws DaoException {

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
                Integer customerId = rs.getInt(9);
                PaymentTrx paymentTrx = new PaymentTrx(id, cardId, startTime, finishTime,
                        value, currency, expiry, pan, customerId);
                paymentsList.add(paymentTrx);

            }

            return paymentsList;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}