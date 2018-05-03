package ru.intervale.course.beans;

import java.sql.Date;

public class PaymentTrx {

    public enum MoneyUnits {
        BYN, USD, EURO;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private final int id;
    private final String dataCard;
    private final Date startTrxTime;
    private final Date finishTrxTime;
    private final int value;
    private final MoneyUnits moneyUnit;

    public PaymentTrx(int id, String dataCard, Date startTrxTime, Date finishTrxTime,
                      int value, MoneyUnits moneyUnit) {
        this.id = id;
        this.dataCard = dataCard;
        this.startTrxTime = startTrxTime;
        this.finishTrxTime = finishTrxTime;
        this.value = value;
        this.moneyUnit = moneyUnit;
    }

    public int getId() {
        return id;
    }

    public String getDataCard() {
        return dataCard;
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

    public MoneyUnits getMoneyUnit() {
        return moneyUnit;
    }

}
