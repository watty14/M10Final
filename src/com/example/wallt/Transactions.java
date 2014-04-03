package com.example.wallt;

import java.util.Calendar;

/**
 *
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class Transactions {

    /**
     * amount: double instance variable.
     */
    private double amount;

    /**
     * type: String instance variable.
     */
    private String type;

    /**
     * calendar: instance variable of Calendar.
     */
    private Calendar calendar;

    /**
     * reason: String instance variable.
     */
    private String reason;

    /**
     * Transactions class.
     * constructor for transactions class.
     * @param amont double
     * @param tp String
     */
    public Transactions(final double amont, final String tp) {
        this.amount = amont;
        this.type = tp;
    }

    /**
     * setReason class.
     * sets the reason for the transaction.
     * @param rsn String
     */
    public final void setReason(final String rsn) {
        this.reason = rsn;
    }

    /**
     * getReason class.
     * gets the reason for the transaction.
     * @return reason String
     */
    public final String getReason() {
        return reason;
    }

    /**
     * setDate class.
     * sets the date for the transaction.
     * @param clndar Calendar
     */
    public final void setDate(final Calendar clndar) {
        this.calendar = clndar;
    }

    /**
     * getDate class.
     * gets the date for the transaction.
     * @return calendar
     */
    public final Calendar getDate() {
        return calendar;
    }

    /**
     * getType method.
     * gets the type of transaction.
     * @return type
     */
    public final String getType() {
        return type;
    }

    /**
     * getAmount method.
     * gets the amount of the transaction.
     * @return amount
     */
    public final double getAmount() {
        return amount;
    }

    /**
     * toString.
     * prints out the amount, type, and reason.
     * @return amount, type, and reason
     */
    public final String toString() {
        return "Amount: " + amount
                + "    Type: " + type
                + "    Reason: " + reason;
    }
}
