package ru.intervale.course.beans;

import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Card extends AbstractEntity {

    private int customerId;
    private String panCard;
    private String expiryCardDate;
    private String registerCardTime;

    public Card() {
        super();
    }

    public Card(int id, int customerId, String panCard,String expiryCardDate,
                String registerCardTime) {
        super(id);
        this.customerId = customerId;
        if (isExpiryCardDateInvalid(expiryCardDate)) {
            LoggerFactory.getLogger(Card.class).error("Illegal expiry card date " +
                    expiryCardDate);
            throw new IllegalArgumentException("Illegal expiry card date " + expiryCardDate);
        }
        this.panCard = panCard;
        this.expiryCardDate = expiryCardDate;
        this.registerCardTime = registerCardTime;
    }

    public Card(int customerId, String panCard, String expiryCardDate) {
        this(0, customerId, panCard, expiryCardDate, null);
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

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public void setExpiryCardDate(String expiryCardDate) {
        this.expiryCardDate = expiryCardDate;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"|%-5d|%-5d|%-22s|%-5s|%-10s|",
                getId(), customerId, panCard, expiryCardDate, registerCardTime);
    }

    private static boolean isExpiryCardDateInvalid(String date) {
        final SimpleDateFormat CARD_EXPIRY_DATE_FORMAT =
                new SimpleDateFormat("MMyy");
        CARD_EXPIRY_DATE_FORMAT.setLenient(true);
        try {
            return !CARD_EXPIRY_DATE_FORMAT.format(CARD_EXPIRY_DATE_FORMAT.parse(date)).equals(date);
        } catch (ParseException e) {
            return true;
        }
    }

}
