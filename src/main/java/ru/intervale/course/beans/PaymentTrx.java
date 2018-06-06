package ru.intervale.course.beans;

import ru.intervale.course.utils.CurrencyUtils;

import java.sql.Time;
import java.util.Date;
import java.util.Locale;

public class PaymentTrx extends AbstractEntity {

    public enum MoneyCurrencies {
        BYN, USD, EURO;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private int cardId;
    private Time startTrxTime;
    private Time finishTrxTime;
    private int value;
    private MoneyCurrencies moneyCurrency;

    public PaymentTrx() {
        super();
    }

    public PaymentTrx(int id, int cardId, Time startTrxTime, Time finishTrxTime,
                      int value, String moneyCurrency) {
        super(id);
        this.cardId = cardId;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = MoneyCurrencies.valueOf(moneyCurrency.toUpperCase());
    }

    public PaymentTrx(int cardId, int value, String currency) {
        this(0, cardId, new Time(new Date().getTime()), null,
                value, currency);
    }

    public int getCardId() {
        return cardId;
    }

    public Time getStartTrxTime() {
        return startTrxTime;
    }

    public Time getFinishTrxTime() {
        return finishTrxTime;
    }

    public int getValue() {
        return value;
    }

    public MoneyCurrencies getMoneyCurrency() {
        return moneyCurrency;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setMoneyCurrency(MoneyCurrencies moneyCurrency) {
        this.moneyCurrency = moneyCurrency;
    }

    public void setMoneyCurrency (String moneyCurrency) {
        this.moneyCurrency = MoneyCurrencies.valueOf(moneyCurrency.toUpperCase());
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "|%-5d|%-5d|%-10s|%-10s|%-10s|%-5s|",
                getId(), cardId, startTrxTime, finishTrxTime,
                CurrencyUtils.getStringCurrencyValue(value), moneyCurrency.getName() );
    }
}
