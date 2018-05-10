package ru.intervale.course.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.dao.interfaces.IPaymentTrxDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTrxJDBCDao extends AbstractJDBCDao<PaymentTrx> implements IPaymentTrxDao {

    private static final String CREATE_PAYMENT_QUERY =
            "INSERT INTO customers.payments (cardId, startTime, finishTime, value, currency) " +
                    "VALUES (?, ?, CURRENT_TIME(), ?, ?)";
    private static final String UPDATE_PAYMENT_QUERY =
            "UPDATE customers.payments SET cardId = ?, value = ?, currency = ? WHERE id = ?";

    private final Logger log = LoggerFactory.getLogger(PaymentTrx.class);

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
    public List<PaymentTrx> parseResultSet(ResultSet rs) throws DaoException {

        try {
            List<PaymentTrx> paymentsList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int cardId = rs.getInt(2);
                String startTime = rs.getString(3);
                String finishTime = rs.getString(4);
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

    @Override
    public boolean create (int cardId, int value, String currency) throws DaoException {
        if (new CardJDBCDao(connection).getEntityById(cardId) == null) {
            log.error("No search any card with id {}. Transaction failed", cardId);
            return false;
        }
        try (PreparedStatement pst = connection.prepareStatement(CREATE_PAYMENT_QUERY)) {
            pst.setInt(1, cardId);
            pst.setTime(2, new Time(new java.util.Date().getTime()));
            pst.setInt(3, value);
            pst.setString(4, currency);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(int id, int cardId, int value, String currency)
            throws DaoException {
        if (getEntityById(id) == null) {
            log.error("Payment with id {} was not found", id);
            return false;
        }
        if (new PaymentTrxJDBCDao(connection) == null) {
            log.error("Card with id {} was not found", cardId);
            return false;
        }
        try (PreparedStatement pst =
                     connection.prepareStatement(UPDATE_PAYMENT_QUERY)){
            pst.setInt(1, cardId);
            pst.setInt(2, value);
            pst.setString(3, currency);
            pst.setInt(4, id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
