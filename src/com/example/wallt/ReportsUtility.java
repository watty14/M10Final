package com.example.wallt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.parse.ParseUser;

/**
 *
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class ReportsUtility {

    /**
     *
     *
     * @param from
     * @param to
     * @return
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
                    if (type.equals("withdraw")) {
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
        String title = "Spending Category Report for " + ParseUser.getCurrentUser().getUsername();
        String date = from.getTime().toString() + " - " + to.getTime().toString();
        str.append(title + "\n");
        str.append(date + "\n");
        for (HashMap.Entry<String, Double> entry : map.entrySet()) {
            String reason = entry.getKey();
            double amount = entry.getValue();
            str.append(reason + ":    " + amount + "\n");
        }
        return str.toString();
    }

    /**
     *
     *
     * @param from
     * @param to
     * @return
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
                    if (type.equals("deposit")) {
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
        str.append(title + "\n");
        str.append(date + "\n");
        for (HashMap.Entry<String, Double> entry : map.entrySet()) {
            String reason = entry.getKey();
            double amount = entry.getValue();
            str.append(reason + ":    " + amount + "\n");
        }
        return str.toString();
    }

    /**
     *
     *
     * @param from
     * @param to
     * @return
     */
    public String generateCashFlowReport(Calendar from, Calendar to) {
        ArrayList<BankAccount> list = filteredBankAccounts(from, to);
        double income = 0;
        double expenses = 0;
        if (list != null) {
            for (BankAccount b : list) {
                ArrayList<Transactions> transactions = (ArrayList<Transactions>) b.getListTrans();
                for (Transactions t : transactions) {
                    String type = t.getType();
                    type = type.toLowerCase();
                    if (type.equals("deposit")) {
                        income = income + t.getAmount();
                    } else if (type.equals("withdraw")) {
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
     *
     *
     * @param from
     * @param to
     * @return
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
     *
     *
     * @param account
     * @param from
     * @param to
     * @return
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
     *
     *
     * @param from
     * @param to
     * @return
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
     *
     *
     * @param list
     * @param from
     * @param to
     * @return
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
