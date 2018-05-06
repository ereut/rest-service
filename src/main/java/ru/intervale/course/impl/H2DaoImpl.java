package ru.intervale.course.impl;


import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;

public class H2DaoImpl implements IDao {
    @Override
    public boolean hasNextCustomer() {
        return false;
    }

    @Override
    public boolean hasNextCard() {
        return false;
    }

    @Override
    public boolean hasNextPaymentTrx() {
        return false;
    }

    @Override
    public Customer nextCustomer() {
        return null;
    }

    @Override
    public Card nextCard() {
        return null;
    }

    @Override
    public PaymentTrx nextPaymentTrx() {
        return null;
    }
}
