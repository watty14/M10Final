package com.example.wallt;


import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class BankAccount implements Parcelable {

    /**
     *
     */
    private String objectId;

    /**
     *
     */
    private String accountNumber;

    /**
     *
     */
    private double balance;

    /**
     *
     */
    private String bankName;

    /**
     *
     */
    private String[] transactions;

    /**
     *
     */
    private ArrayList<Transactions> listOfTransactions;

    /**
     *
     *
     * @param objectId 
     * @param accountNumber
     * @param balance
     * @param bankName
     * @param transactions
     */
    public BankAccount(String objectId, String accountNumber, double balance,
            String bankName, String[] transactions) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.bankName = bankName;
        this.objectId = objectId;
        this.transactions = transactions;
    }

    /**
     *
     *
     * @param list
     */
    public void setListOfTransactions(ArrayList<Transactions> list) {
        listOfTransactions = list;
    }

    /**
     *
     *
     * @return
     */
    public ArrayList<Transactions> getListOfTransactions() {
        return listOfTransactions;
    }

    /**
     *
     *
     * @return
     */
    public String[] getTransaction() {
        return transactions;
    }

    /**
     *
     *
     * @return
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     *
     * @return
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     *
     * @return
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     *
     * @return
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     *
     * @param amount
     */
    public void setBalance(double amount) {
        balance = amount;
    }

    /**
     *
     *
     * @return
     */
    public String toString() {
        return "Account Number: " + accountNumber
                + " Balance: " + balance
                + " Bank Name: " + bankName;
    }

    /**
     *
     *
     * @param source
     */
    public BankAccount(Parcel source) {
        objectId = source.readString();
        accountNumber = source.readString();
        balance = source.readDouble();
        bankName = source.readString();
        //source.readStringArray(transactions);
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(accountNumber);
        dest.writeDouble(balance);
        dest.writeString(bankName);
          //dest.writeStringArray(transactions);
    }

    /**
    *
    *
    * 
    */
    public static final Parcelable.Creator<BankAccount> CREATOR =
            new Parcelable.Creator<BankAccount>() {

            /**
             *
             *
             * @param in
             * @return
             */
            public BankAccount createFromParcel(Parcel in) {
                return new BankAccount(in);
            }

            /**
             *
             *
             * @param size
             * @return
             */
            public BankAccount[] newArray(int size) {
                return new BankAccount[size];
            }
        };

}
