package ru.intervale.course.beans;

import java.sql.Date;

public class Card {

    private final int id;
    private final int clientId;
    private final String panCard;
    private final Date expiryCardTime;
    private final Date registerCardTime;

    public Card(int id, int clientId, String panCard, Date expiryCardTime,
                Date registerCardTime) {
        this.id = id;
        this.clientId = clientId;
        this.panCard = panCard;
        this.expiryCardTime = expiryCardTime;
        this.registerCardTime = registerCardTime;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
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
