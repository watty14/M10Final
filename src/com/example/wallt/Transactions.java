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
     *
     */
    private double amount;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private Calendar calendar;

    /**
     *
     */
    private String reason;

    /**
     *
     *
     * @param amount
     * @param type
     */
    public Transactions(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    /**
     *
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     *
     * @param calendar
     */
    public void setDate(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     *
     *
     * @return
     */
    public Calendar getDate() {
        return calendar;
    }

    /**
     *
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     *
     * @return
     */
    public double getAmount() {
        return amount;
    }

    /**
     *
     *
     * @return
     */
    public String toString() {
        return "Amount: " + amount
                + "    Type: " + type
                + "    Reason: " + reason;
    }
}
