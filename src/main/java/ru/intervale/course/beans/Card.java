package ru.intervale.course.beans;

import ru.intervale.course.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Card extends AbstractEntity {

    private static final String CARD_PRINT_FORMAT = "|%-5d|%-5d|%-22s|%-5s|%-20s|%-15s|";

    private Integer customerId;
    private String panCard;
    private Date expiryCardDate;
    private Date registerCardTime;
    private String title;

    public Card() {}

    public Card(Integer id, Integer customerId, String panCard, String expiryCardDate,
                Date registerCardTime, String title) {
        super(id);
        this.customerId = customerId;

        try {
            this.expiryCardDate = expiryCardDate == null ? null :
                    Constants.CARD_EXPIRY_DATE_FORMAT.parse(expiryCardDate);
        } catch (ParseException e) {
           throw new IllegalArgumentException(Constants.ILLEGAL_EXPIRY_CARD_DATE_MESSAGE, e);
        }
        this.panCard = panCard;
        this.registerCardTime = registerCardTime;
        this.title = title;
    }

    public Card(Integer customerId, String panCard, String expiryCardDate, String title) {
        this(null, customerId, panCard, expiryCardDate, null, title);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getPanCard() {
        return panCard;
    }

    public String getTitle() {
        return title;
    }

    public Date getExpiryCardDate() {
        return expiryCardDate;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setExpiryCardDate(Date expiryCardDate) {
        this.expiryCardDate = expiryCardDate;
    }

    public void setExpiryCardDate(String expiryCardDate) {
        try {
            this.expiryCardDate = expiryCardDate == null ? null : Constants.CARD_EXPIRY_DATE_FORMAT.parse(expiryCardDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(Constants.ILLEGAL_EXPIRY_CARD_DATE_MESSAGE + expiryCardDate, e);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRegisterCardTime(Date registerCardTime) {
        this.registerCardTime = registerCardTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, CARD_PRINT_FORMAT, getId(), customerId, panCard,
                Constants.CARD_EXPIRY_DATE_FORMAT.format(expiryCardDate),
                new SimpleDateFormat(Constants.DATE_PATTERN).format(registerCardTime),
                title);
    }

}
