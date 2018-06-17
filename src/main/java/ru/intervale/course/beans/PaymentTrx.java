package ru.intervale.course.beans;

import ru.intervale.course.Constants;
import ru.intervale.course.utils.CurrencyUtils;

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

    public PaymentTrx() {}

    public PaymentTrx(Integer id, int cardId, Date startTrxTime, Date finishTrxTime,
                      int value, String moneyCurrency) {
        super(id);
        this.cardId = cardId;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = MoneyCurrencies.valueOf(moneyCurrency.toUpperCase());
    }

    public PaymentTrx(Integer cardId, int value, String currency) {
        this(null, cardId, new Date(), null,
                value, currency);
    }

    public int getCardId() {
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

    public void setCardId(int cardId) {
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

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
        return String.format(Locale.ENGLISH, PAYMENT_PRINT_FORMAT,
                getId(), cardId, sdf.format(startTrxTime), sdf.format(finishTrxTime),
                CurrencyUtils.getStringCurrencyValue(value), moneyCurrency.getName() );
    }


}
