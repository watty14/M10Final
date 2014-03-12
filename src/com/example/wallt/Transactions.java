package com.example.wallt;

import java.util.Calendar;

public class Transactions {
	
	private double amount;
	private String type;
	private Calendar calendar;
	private String reason;
	
	public Transactions(double amount, String type) {
		this.amount = amount;
		this.type = type;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setDate(Calendar calendar) {
		this.calendar = calendar;
	}
	
	public Calendar getDate() {
		return calendar;
	}
	
	public String getType() {
		return type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String toString() {
		return "Amount: " + amount
				+ "    Type: " + type
				+ "    Reason: " + reason;
	}
}
