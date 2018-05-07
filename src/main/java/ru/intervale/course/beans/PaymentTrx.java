package ru.intervale.course.beans;

import java.sql.Date;

public class PaymentTrx extends AbstractEntity {

    public enum MoneyCurrencies {
        BYN, USD, EURO;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private final int cardId;
    private final Date startTrxTime;
    private final Date finishTrxTime;
    private final int value;
    private final MoneyCurrencies moneyCurrency;

    public PaymentTrx(int id, int cardId, Date startTrxTime, Date finishTrxTime,
                      int value, MoneyCurrencies moneyCurrency) {
        super(id);
        this.cardId = cardId;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyCurrency = moneyCurrency;
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

}
