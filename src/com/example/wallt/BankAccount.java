package com.example.wallt;


import android.os.Parcel;
import android.os.Parcelable;

public class BankAccount implements Parcelable {
	
	private String objectId;
	private String accountNumber;
	private double balance;
	private String bankName;
	private String[] transactions;
	
	public BankAccount(String objectId, String accountNumber, double balance,
			String bankName, String[] transactions) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.bankName = bankName;
		this.objectId = objectId;
		this.transactions = transactions;
	}
	
	public String[] getTransaction() {
		return transactions;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public String getObjectId() {
		return objectId;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBalance(double amount) {
		balance = amount;
	}
	
	public String toString() {
		return "Account Number: " + accountNumber 
				+ " Balance: " + balance 
				+ " Bank Name: " + bankName;
	}
	
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

	
	public static final Parcelable.Creator<BankAccount> CREATOR = 
			new Parcelable.Creator<BankAccount>() {
        public BankAccount createFromParcel(Parcel in) {
            return new BankAccount(in); 
        }

        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };
    
}
