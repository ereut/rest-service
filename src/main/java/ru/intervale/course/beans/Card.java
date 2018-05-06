package ru.intervale.course.beans;

import java.sql.Date;

public class Card {

    private final int id;
    private final Customer customer;
    private final String panCard;
    private final Date expiryCardTime;
    private final Date registerCardTime;

    public Card(int id, Customer customer, String panCard, Date expiryCardTime,
                Date registerCardTime) {
        this.id = id;
        this.customer = customer;
        this.panCard = panCard;
        this.expiryCardTime = expiryCardTime;
        this.registerCardTime = registerCardTime;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPanCard() {
        return panCard;
    }

    public Date getExpiryCardTime() {
        return expiryCardTime;
    }

    public Date getRegisterCardTime() {
        return registerCardTime;
    }

}
