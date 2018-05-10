package ru.intervale.course.beans;

import java.util.Locale;

public class Card extends AbstractEntity {

    private final int customerId;
    private final String panCard;
    private final String expiryCardDate;
    private final String registerCardTime;

    public Card(int id, int customerId, String panCard,String expiryCardDate,
                String registerCardTime) {
        super(id);
        this.customerId = customerId;
        this.panCard = panCard;
        this.expiryCardDate = expiryCardDate;
        this.registerCardTime = registerCardTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPanCard() {
        return panCard;
    }

    public String getExpiryCardDate() {
        return expiryCardDate;
    }

    public String getRegisterCardTime() {
        return registerCardTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"|%-5d|%-5d|%-22s|%-5s|%-10s|",
                getId(), customerId, panCard, expiryCardDate, registerCardTime);
    }

}
