package ru.intervale.course.beans;

import java.sql.Date;
import java.sql.Time;

public class Card extends AbstractEntity {

    private final int customerId;
    private final String panCard;
    private final Date expiryCardTime;
    private final Time registerCardTime;

    public Card(int id, int customerId, String panCard, Date expiryCardTime,
                Time registerCardTime) {
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

    public Time getRegisterCardTime() {
        return registerCardTime;
    }

}
