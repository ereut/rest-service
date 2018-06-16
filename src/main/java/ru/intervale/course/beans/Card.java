package ru.intervale.course.beans;

import org.slf4j.LoggerFactory;
import ru.intervale.course.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Card extends AbstractEntity {

    private static final String ILLEGAL_EXPIRY_CARD_DATE_MESSAGE = "Illegal expiry card date ";
    private static final String CARD_PRINT_FORMAT = "|%-5d|%-5d|%-22s|%-5s|%-20s|%-15s|";
    private static final String EXPIRY_DATE_PATTERN = "MMyy";

    private int customerId;
    private String panCard;
    private String expiryCardDate;
    private Date registerCardTime;
    private String title;

    public Card() {}

    public Card(int id, int customerId, String panCard,String expiryCardDate,
                Date registerCardTime, String title) {
        super(id);
        this.customerId = customerId;
        if (isExpiryCardDateInvalid(expiryCardDate)) {
            LoggerFactory.getLogger(Card.class).error(ILLEGAL_EXPIRY_CARD_DATE_MESSAGE +
                    expiryCardDate);
            throw new IllegalArgumentException(ILLEGAL_EXPIRY_CARD_DATE_MESSAGE + expiryCardDate);
        }
        this.panCard = panCard;
        this.expiryCardDate = expiryCardDate;
        this.registerCardTime = registerCardTime;
        this.title = title;
    }

    public Card(int customerId, String panCard, String expiryCardDate, String title) {
        this(0, customerId, panCard, expiryCardDate, null, title);
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPanCard() {
        return panCard;
    }

    public String getTitle() {
        return title;
    }

    public String getExpiryCardDate() {
        return expiryCardDate;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setExpiryCardDate(String expiryCardDate) {
        this.expiryCardDate = expiryCardDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, CARD_PRINT_FORMAT, getId(), customerId, panCard,
                expiryCardDate, new SimpleDateFormat(Constants.DATE_PATTERN).format(registerCardTime),
                getFieldForPrint(title));
    }

    private static boolean isExpiryCardDateInvalid(String date) {
        final SimpleDateFormat CARD_EXPIRY_DATE_FORMAT =
                new SimpleDateFormat(EXPIRY_DATE_PATTERN);
        CARD_EXPIRY_DATE_FORMAT.setLenient(true);
        try {
            return !CARD_EXPIRY_DATE_FORMAT.format(CARD_EXPIRY_DATE_FORMAT.parse(date)).equals(date);
        } catch (ParseException e) {
            return true;
        }

    }

}
