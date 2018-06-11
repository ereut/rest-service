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

    private int cardId;
    private Date startTrxTime;
    private Date finishTrxTime;
    private int value;
    private MoneyCurrencies moneyCurrency;

    public PaymentTrx(int id, int cardId, Date startTrxTime, Date finishTrxTime,
                      int value, String moneyCurrency) {
        super(id);
        this.cardId = cardId;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = MoneyCurrencies.valueOf(moneyCurrency.toUpperCase());
    }

    public PaymentTrx(int cardId, int value, String currency) {
        this(0, cardId, new Date(), null,
                value, currency);
    }

    public int getCardId() {
        return cardId;
    }

    public Date getStartTrxTime() {
        return startTrxTime;
    }

    public Date getFinishTrxTime() {
        return finishTrxTime;
    }

    public int getValue() {
        return value;
    }

    public MoneyCurrencies getMoneyCurrency() {
        return moneyCurrency;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
        return String.format(Locale.ENGLISH, PAYMENT_PRINT_FORMAT,
                getId(), cardId, sdf.format(startTrxTime), sdf.format(finishTrxTime),
                CurrencyUtils.getStringCurrencyValue(value), moneyCurrency.getName() );
    }


}
