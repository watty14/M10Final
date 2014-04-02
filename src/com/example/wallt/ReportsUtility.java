package com.example.wallt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.parse.ParseUser;

/**
 * ReportsUtility class allows to to take input dates and types of date to
 * create the proper report for the users.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class ReportsUtility {
	
	/**
     * deposit : Instance Variable for a String.
     */
	private String deposit = "deposit";
	
	/**
     * withdraw : Instance Variable for a String.
     */
	private String withdraw = "withdraws";
	
	/**
     * space : Instance Variable for a String.
     */
	private String space = ":    ";
	
	/**
     * newLine : Instance Variable for a String.
     */
	private String newLine = "\n";

    /**
     * generateSpendingReport creates a report for spending.
     *
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return String of transactions and reasons.
     */
    public String generateSpendingReport(Calendar from, Calendar to) {
        ArrayList<BankAccount> list = filteredBankAccounts(from, to);

        HashMap<String, Double> map = new HashMap<String, Double>();
        if (list != null) {
            for (BankAccount b : list) {
                ArrayList<Transactions> transactions = (ArrayList<Transactions>) b.getListTrans();
                for (Transactions t : transactions) {
                    String type = t.getType();
                    type = type.toLowerCase();
                    if (type.equals(withdraw)) {
                        String reason1 = t.getReason();
                        reason1 = reason1.toLowerCase();
                        double amount = t.getAmount();
                        if (map.containsKey(reason1)) {
                            double current = map.get(reason1);
                            amount = amount + current;
                        }
                        map.put(reason1, amount);
                    }
                }
            }
        }
        StringBuilder str = new StringBuilder();
        String title = "Spending Category Report for " + ParseUser.getCurrentUser().getUsername();
        String date = from.getTime().toString() + " - " + to.getTime().toString();
        str.append(title + newLine);
        str.append(date + newLine);
        for (HashMap.Entry<String, Double> entry : map.entrySet()) {
            String reason = entry.getKey();
            double amount = entry.getValue();
            str.append(reason + space + amount + newLine);
        }
        return str.toString();
    }

    /**
     *generateIncomeReport method creates a report based on Income.
     *
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return String of transactions and reasons.
     */
    public String generateIncomeReport(Calendar from, Calendar to) {
        ArrayList<BankAccount> list = filteredBankAccounts(from, to);

        HashMap<String, Double> map = new HashMap<String, Double>();
        if (list != null) {
            for (BankAccount b : list) {
                ArrayList<Transactions> transactions = (ArrayList<Transactions>) b.getListTrans();
                for (Transactions t : transactions) {
                    String type = t.getType();
                    type = type.toLowerCase();
                    if (type.equals(deposit)) {
                        String reason = t.getReason();
                        reason = reason.toLowerCase();
                        double amount = t.getAmount();
                        if (map.containsKey(reason)) {
                            double current = map.get(reason);
                            amount = amount + current;
                        }
                        map.put(reason, amount);
                    }
                }
            }
        }
        StringBuilder str = new StringBuilder();
        String title = "Income Category Report for " + ParseUser.getCurrentUser().getUsername();
        String date = from.getTime().toString() + " - " + to.getTime().toString();
        str.append(title + newLine);
        str.append(date + newLine);
        for (HashMap.Entry<String, Double> entry : map.entrySet()) {
            String reason = entry.getKey();
            double amount = entry.getValue();
            str.append(reason + space + amount + newLine);
        }
        return str.toString();
    }

    /**
     * generateCashFlowReport method creates a report based on cash flow.
     *
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return String of transactions and reasons.
     */
    public String generateCashFlowReport(Calendar from, Calendar to) {
        ArrayList<BankAccount> list2 = filteredBankAccounts(from, to);
        double income = 0;
        double expenses = 0;
        if (list2 != null) {
            for (BankAccount b : list2) {
                ArrayList<Transactions> transactions = (ArrayList<Transactions>) b.getListTrans();
                for (Transactions t : transactions) {
                    String type = t.getType();
                    type = type.toLowerCase();
                    if (type.equals(deposit)) {
                        income = income + t.getAmount();
                    } else if (type.equals(withdraw)) {
                        expenses = expenses + t.getAmount();
                    }
                }
            }
        }
        StringBuilder str = new StringBuilder();
        String title = "Cash Flow Report for " + ParseUser.getCurrentUser().getUsername();
        String date = from.getTime().toString() + " - " + to.getTime().toString();
        String inc = "Income : " + income;
        String exp = "Expenses : " + expenses;
        String flow = "Flow : " + (income - expenses);
        str.append(title + "\n");
        str.append(date + "\n");
        str.append(inc + "\n");
        str.append(exp + "\n");
        str.append(flow + "\n");
        return str.toString();
    }

    /**
     * generateAccountListingReport method creates a report based on AccountList.
     *
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return String of transactions and reasons.
     */
    public String generateAccountListingReport(Calendar from, Calendar to) {
        ArrayList<BankAccount> list = filteredBankAccounts(from, to);
        StringBuilder str = new StringBuilder();
        String title = "Account Listings Report for " + ParseUser.getCurrentUser().getUsername();
        String date = from.getTime().toString() + " - " + to.getTime().toString();
        str.append(title + "\n");
        str.append(date + "\n");
        if (list != null) {
            for (BankAccount b : list) {
                String name = b.getBankName();
                double balance = b.getBalance();
                str.append(name + "   " + balance + "\n");
            }
        }
        return str.toString();
    }

    /**
     * generateTransactionHistory method creates a transaction history for an bank account.
     *
     * @param account : Bank account object
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return String of transactions and reasons.
     */
    public String generateTransactionHistory(BankAccount account,
            Calendar from, Calendar to) {
        ArrayList<Transactions> initialList =  ServerUtility.getTransactions(account);
        initialList = filterByDate(initialList, from, to);
        StringBuilder str = new StringBuilder();
        String title = "Transaction History Report for " + account.getBankName();
        String date = from.getTime().toString() + " - " + to.getTime().toString();
        str.append(title + "\n");
        str.append(date + "\n");
        if (initialList != null) {
            for (Transactions t : initialList) {
                str.append(t.toString() + "\n");
            }
        }
        return str.toString();
    }

    /**
     * filteredBankAccounts method filters out the date range.
     *
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return ArrayList<BankAccount> : list of bankaccounts with valid informations
     */
    private ArrayList<BankAccount> filteredBankAccounts(Calendar from, Calendar to) {
        ArrayList<BankAccount> accounts = ServerUtility.getReportData();
        for (BankAccount b : accounts) {
            ArrayList<Transactions> newList = filterByDate((ArrayList<Transactions>) b.getListTrans(), from, to);
            b.setListTrans(newList);
        }
        return accounts;
    }

    /**
     * filterByDate method is a helper method for filteredBankAccounts which
     * helps filter out bank accounts by date ranges.
     *
     * @param list : list of bank accounts.
     * @param from : starting date of the report.
     * @param to   : ending date of the report.
     * @return ArrayList<Transactions> : List of transactions.
     */
    private ArrayList<Transactions> filterByDate(ArrayList<Transactions> list, Calendar from, Calendar to) {
        ArrayList<Transactions> finalList = new ArrayList<Transactions>();
        for (Transactions t : list) {
            Calendar thisDate = t.getDate();
            if (from.compareTo(thisDate) <= 0 && to.compareTo(thisDate) >= 0) {
                finalList.add(t);
            }
        }
        return finalList;
    }
}
