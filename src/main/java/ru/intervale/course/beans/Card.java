package ru.intervale.course.beans;

import java.sql.Date;

public class Card extends AbstractEntity {

    private final int customerId;
    private final String panCard;
    private final Date expiryCardTime;
    private final Date registerCardTime;

    public Card(int id, int customerId, String panCard, Date expiryCardTime,
                Date registerCardTime) {
        super(id);
        this.customerId = customerId;
        this.panCard = panCard;
        this.expiryCardTime = expiryCardTime;
        this.registerCardTime = registerCardTime;
    }

    public int getCustomerId() {
        return customerId;
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
