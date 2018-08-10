package ru.intervale.course.dao;

import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;
import ru.intervale.course.impl.CardJDBCDaoImpl;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;
import ru.intervale.course.impl.PaymentTrxJDBCDaoImpl;

public class DaoFactory {

    private final static DataSources DEFAULT_DATA_SOURCE = DataSources.DATABASE;

    private enum DataSources {

        DATABASE {

            @Override
            IDao<Customer> getCustomerDaoImpl() throws DaoException {
                return new CustomerJDBCDaoImpl();
            }

            @Override
            IDao<Card> getCardDaoImpl() throws DaoException {
                return new CardJDBCDaoImpl();
            }

            @Override
            IDao<PaymentTrx> getPaymentTrxDaoImpl() throws DaoException {
                return new PaymentTrxJDBCDaoImpl();
            }

        };

        abstract  IDao<Customer> getCustomerDaoImpl() throws DaoException;
        abstract IDao<Card> getCardDaoImpl() throws DaoException;
        abstract IDao<PaymentTrx> getPaymentTrxDaoImpl() throws DaoException;

    }

    public static IDao<Customer> getCustomerDaoImplFromFactory() throws DaoException {
        return DEFAULT_DATA_SOURCE.getCustomerDaoImpl();
    }

    public static IDao<Card> getCardDaoImplFromFactory() throws DaoException {
        return DEFAULT_DATA_SOURCE.getCardDaoImpl();
    }

    public static IDao<PaymentTrx> getPaymentTrxDaoImplFromFactory() throws DaoException {
        return DEFAULT_DATA_SOURCE.getPaymentTrxDaoImpl();
    }

}
