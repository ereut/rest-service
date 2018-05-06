package ru.intervale.course.impl;

import ru.intervale.course.beans.Card;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.beans.PaymentTrx;

public interface IDao {

    boolean hasNextCustomer();
    boolean hasNextCard();
    boolean hasNextPaymentTrx();

    Customer nextCustomer();
    Card nextCard();
    PaymentTrx nextPaymentTrx();

}
