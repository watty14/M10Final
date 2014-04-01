package com.example.wallt;


import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class describing the behavior of a bank account object.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class BankAccount implements Parcelable {

    /**
     * Unique objectId.
     */
    private String objectId;

    /**
     * Account number identifier.
     */
    private String accountNumber;

    /**
     * Current balance of account.
     */
    private double balance;

    /**
     * Name of hosting bank.
     */
    private String bankName;

    /**
     * Array of transaction ids.
     */
    private String[] transactions;

    /**
     * Array List of transaction objects.
     */
    private ArrayList<Transactions> listOfTransactions;

    /**
     * Constructor used in the creation of a bankAccount bject.
     *
     * @param objectId1 Unique id
     * @param accountNumber1 Account number of bank account
     * @param balance1 Current balance
     * @param bankName1 Name of hosting bank
     * @param transactions1 Array of transactions
     */
    public BankAccount(String objectId1, String accountNumber1, double balance1,
            String bankName1, String[] transactions1) {
        this.accountNumber = accountNumber1;
        this.balance = balance1;
        this.bankName = bankName1;
        this.objectId = objectId1;
        this.transactions = transactions1;
    }

    /**
     * Sets transactions.
     *
     * @param list List to be set
     */
    public void setListOfTransactions(ArrayList<Transactions> list) {
        listOfTransactions = list;
    }

    /**
     * Getter for transactions.
     *
     * @return transactions
     */
    public ArrayList<Transactions> getListOfTransactions() {
        return listOfTransactions;
    }

    /**
     * Getter for transactions.
     *
     * @return transactions
     */
    public String[] getTransactions() {
        return transactions;
    }

    /**
     * Getter for account number.
     *
     * @return account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Getter for objectId.
     *
     * @return objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Getter for current balance.
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Getter for bank name.
     *
     * @return bankNAme
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Getter for balance.
     *
     * @param amount Current balance
     */
    public void setBalance(double amount) {
        balance = amount;
    }

    /**
     * To string method.
     *
     * @return String
     */
    public String toString() {
        return "Account Number: " + accountNumber
                + " Balance: " + balance
                + " Bank Name: " + bankName;
    }

    /**
     * Parces a bank account object.
     *
     * @param source Source of parcel
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
    * Creates a parelable item.
    *
    *
    */
    public static final Parcelable.Creator<BankAccount> CREATOR =
            new Parcelable.Creator<BankAccount>() {

            /**
             * Create a parcel
             *
             * @param in Parcelt
             * @return BankAccount object
             */
            public BankAccount createFromParcel(Parcel in) {
                return new BankAccount(in);
            }

            /**
             * Creates array of bankaccount
             *
             * @param size Size to create
             * @return Array of bankaccounts
             */
            public BankAccount[] newArray(int size) {
                return new BankAccount[size];
            }
        };

}
