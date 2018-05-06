package ru.intervale.course.beans;

import java.sql.Date;

public class PaymentTrx {

    public enum MoneyCurrencies {
        BYN, USD, EURO;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private final int id;
    private final Card card;
    private final Date startTrxTime;
    private final Date finishTrxTime;
    private final int value;
    private final MoneyCurrencies moneyCurrency;

    public PaymentTrx(int id, Card card, Date startTrxTime, Date finishTrxTime,
                      int value, MoneyCurrencies moneyCurrency) {
        this.id = id;
        this.card = card;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = moneyCurrency;
    }

    public int getId() {
        return id;
    }

    public Card getDataCard() {
        return card;
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

    public MoneyCurrencies getMoneyUnit() {
        return moneyCurrency;
    }

}
