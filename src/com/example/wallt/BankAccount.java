package com.example.wallt;


import java.util.ArrayList;
import java.util.List;

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
            public BankAccount createFromParcel(final Parcel inParcel) {
                return new BankAccount(inParcel);
            }

            /**
             * Creates array of bankaccount
             *
             * @param size Size to create
             * @return Array of bankaccounts
             */
            public BankAccount[] newArray(final int size) {
                return new BankAccount[size];
            }
        };


    /**
     * Array List of transaction objects.
     */
    private List<Transactions> listTrans;

    /**
     * Getter for transactions.
     * @return transactions.
     */
    public final List<Transactions> getListTrans() {
        return listTrans;
    }

    /**
     * Setter for transactions.
     * @param listTrans1 to be set
     */
    public final void setListTrans(final List<Transactions> listTrans1) {
        this.listTrans = (ArrayList<Transactions>) listTrans1;
    }

    /**
     * Setter for object id.
     * @param objectId1 Id to be set.
     */
    public final void setObjectId(final String objectId1) {
        this.objectId = objectId1;
    }

    /**
     * Setter for account number.
     * @param accountNumber1 Number to be set.
     */
    public final void setAccountNumber(final String accountNumber1) {
        this.accountNumber = accountNumber1;
    }

    /**
     * Setter for bank name.
     * @param bankName1 Banke name to be set.
     */
    public final void setBankName(final String bankName1) {
        this.bankName = bankName1;
    }

    /**
     * Setter for transactions.
     * @param transactions1 Transaction to be set.
     */
    public final void setTransactions(final String[] transactions1) {
        transactions = transactions1;
    }



    /**
     * Constructor used in the creation of a bankAccount object.
     *
     * @param objectId1 Unique id
     * @param accountNumber1 Account number of bank account
     * @param balance1 Current balance
     * @param bankName1 Name of hosting bank
     * @param transactions1 Array of transactions
     */
    public BankAccount(final String objectId1, final String accountNumber1,
            final double balance1, final String bankName1,
            final String[] transactions1) {
        this.accountNumber = accountNumber1;
        this.balance = balance1;
        this.bankName = bankName1;
        this.objectId = objectId1;
        transactions = transactions1;
    }

    /**
     * Getter for transactions.
     *
     * @return transactions
     */
    public final String[] getTransactions() {
        return transactions;
    }

    /**
     * Getter for account number.
     *
     * @return account number
     */
    public final String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Getter for objectId.
     *
     * @return objectId
     */
    public final String getObjectId() {
        return objectId;
    }

    /**
     * Getter for current balance.
     *
     * @return balance
     */
    public final double getBalance() {
        return balance;
    }

    /**
     * Getter for bank name.
     *
     * @return bankNAme
     */
    public final String getBankName() {
        return bankName;
    }

    /**
     * Getter for balance.
     *
     * @param amount Current balance
     */
    public final void setBalance(final double amount) {
        balance = amount;
    }

    /**
     * To string method.
     *
     * @return String
     */
    public final String toString() {
        return "Account Number: " + accountNumber
                + " Balance: " + balance
                + " Bank Name: " + bankName;
    }

    /**
     * Parces a bank account object.
     *
     * @param source Source of parcel
     */
    public BankAccount(final Parcel source) {
        objectId = source.readString();
        accountNumber = source.readString();
        balance = source.readDouble();
        bankName = source.readString();
        //source.readStringArray(transactions);
    }

    @Override
    public final int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public final void writeToParcel(final Parcel dest,
            final int flags) {
        dest.writeString(objectId);
        dest.writeString(accountNumber);
        dest.writeDouble(balance);
        dest.writeString(bankName);
          //dest.writeStringArray(transactions);
    }

}
