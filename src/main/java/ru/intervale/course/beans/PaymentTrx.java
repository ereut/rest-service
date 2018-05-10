package ru.intervale.course.beans;

import ru.intervale.course.utils.CurrencyUtils;

import java.util.Locale;

public class PaymentTrx extends AbstractEntity {

    public enum MoneyCurrencies {
        BYN, USD, EURO;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private final int cardId;
    private final String startTrxTime;
    private final String finishTrxTime;
    private final int value;
    private final MoneyCurrencies moneyCurrency;

    public PaymentTrx(int id, int cardId, String startTrxTime, String finishTrxTime,
                      int value, MoneyCurrencies moneyCurrency) {
        super(id);
        this.cardId = cardId;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = moneyCurrency;
    }

    public PaymentTrx(int id, int cardId, String startTrxTime, String finishTrxTime,
                      int value, String currency) {
        this(id, cardId, startTrxTime, finishTrxTime,
                value, MoneyCurrencies.valueOf(currency.toUpperCase()));

    }

    public int getCardId() {
        return cardId;
    }

    public String getStartTrxTime() {
        return startTrxTime;
    }

    public String getFinishTrxTime() {
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
        return String.format(Locale.ENGLISH, "|%-5d|%-5d|%-10s|%-10s|%-10s|%-5s|",
                getId(), cardId, startTrxTime, finishTrxTime,
                CurrencyUtils.getStringCurrencyValue(value), moneyCurrency.getName() );
    }
}
