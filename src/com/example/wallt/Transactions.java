package com.example.wallt;

public class Transactions {
	
	private double amount;
	private String type;
	
	public Transactions(double amount, String type) {
		this.amount = amount;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String toString() {
		return "Amount: " + amount
				+ "    Type: " + type;
	}
}
