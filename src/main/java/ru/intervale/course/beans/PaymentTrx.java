package ru.intervale.course.beans;

import ru.intervale.course.Constants;
import ru.intervale.course.utils.CurrencyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentTrx extends AbstractEntity {

    private static final String PAYMENT_PRINT_FORMAT = "|%-5d|%-5d|%-20s|%-20s|%-10s|%-5s|";

    public enum MoneyCurrencies {
        BYN, USD, EURO;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private Integer cardId;
    private Date startTrxTime;
    private Date finishTrxTime;
    private int value;
    private MoneyCurrencies moneyCurrency;
    private Date expiry;
    private String pan;
    private Integer customerId;

    public PaymentTrx() {}

    public PaymentTrx(Integer id, Integer cardId, Date startTrxTime, Date finishTrxTime,
                      int value, String moneyCurrency, String expiry, String pan, Integer customerId) {
        super(id);
        this.cardId = cardId;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = MoneyCurrencies.valueOf(moneyCurrency.toUpperCase());
        try {
            this.expiry = expiry == null ? null : Constants.CARD_EXPIRY_DATE_FORMAT.parse(expiry);
        } catch (ParseException e) {
            throw new IllegalArgumentException(Constants.ILLEGAL_EXPIRY_CARD_DATE_MESSAGE + expiry, e);
        }
        this.pan = pan;
        this.customerId = customerId;
    }

    public PaymentTrx(Integer cardId, int value, String currency, String expiry, String pan,
                      Integer customerId) {
        this(null, cardId, new Date(), null,value,
                currency, expiry, pan, customerId);
    }

    public Integer getCardId() {
        return cardId;
    }

    public MoneyCurrencies getMoneyCurrency() {
        return moneyCurrency;
    }

    public int getValue() {
        return value;
    }

    public Date getStartTrxTime() {
        return startTrxTime;
    }

    public Date getExpiry() {
        return expiry;
    }

    public String getPan() {
        return pan;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Date getFinishTrxTime() {
        return finishTrxTime;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public void setMoneyCurrency(MoneyCurrencies moneyCurrency) {
        this.moneyCurrency = moneyCurrency;
    }

    public void setMoneyCurrency(String moneyCurrency) {
        this.moneyCurrency = MoneyCurrencies.valueOf(moneyCurrency);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setStartTrxTime(Date startTrxTime) {
        this.startTrxTime = startTrxTime;
    }

    public void setFinishTrxTime(Date finishTrxTime) {
        this.finishTrxTime = finishTrxTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
        return String.format(Locale.ENGLISH, PAYMENT_PRINT_FORMAT , getId(), cardId, sdf.format(startTrxTime),
                sdf.format(finishTrxTime), CurrencyUtils.getStringCurrencyValue(value), moneyCurrency.getName());
    }

}
