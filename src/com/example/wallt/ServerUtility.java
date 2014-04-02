package com.example.wallt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * server utility that connects the app with the server.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class ServerUtility {

	/**
	 * server utility class' singleton design.
	 */
	private static ServerUtility instance = null;
	/**
	 * constructor.
	 */
	protected ServerUtility() {
		instance = this;
	}
	/**
	 * gets instance of server utility.
	 * @return instance of server utility
	 */
	public static ServerUtility getInstance() {
		if (instance == null) {
			new ServerUtility();
		}
		return instance;
	}

    //Table names

    /**
     *
     */
    public static final String TABLE_USER = "User";

    /**
     *
     */
    public static final String TABLE_OWNER = "AccountOwners";

    /**
     *
     */
    public static final String TABLE_BANKACCOUNT = "BankAccount";

    /**
     *
     */
    public static final String TABLE_TRANSACTIONS = "Transactions";

    //Column names

    /**
     *
     */
    public static final String COLUMN_ID = "objectId";

    /**
     *
     */
    public static final String COLUMN_USERNAME = "username";

    /**
     *
     */
    public static final String COLUMN_BANKACCOUNTS = "bankaccounts";

    /**
     *
     */
    public static final String COLUMN_BANKNAME = "bankname";

    /**
     *
     */
    public static final String COLUMN_ACCOUNTNUMBER = "accountnumber";

    /**
     *
     */
    public static final String COLUMN_BALANCE = "balance";

    /**
     *
     */
    public static final String COLUMN_TRANSACTIONS = "transactions";

    /**
     *
     */
    public static final String COLUMN_AMOUNT = "amount";

    /**
     *
     */
    public static final String COLUMN_TRANSACTIONTYPE = "transactiontype";

    /**
     *
     */
    public static final String COLUMN_DATE = "createdAt";

    /**
     *
     */
    public static final String COLUMN_TRANSACTIONREASON = "reason";

    /**
     * logs in user.
     *
     * @param username username for login
     * @param password password for login
     * @return login validity
     */
    public final Boolean logInUser(final String username,
    		final String password) {
        ParseUser user = null;
        try {
            user = ParseUser.logIn(username, password);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return user != null;
    }

    /**
     * checks duplicate user account and signs up user.
     *
     * @param username user name
     * @param password password for account
     * @return boolean value for sign up success
     */
    public final boolean signUpUser(final String username,
    		final String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        boolean created = false;
        try {
            user.signUp();
            addOwner(username);
            created = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return created;
    }
/*
    private static ParseObject queryObject(String key, String value,
            String table) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(table);
        query.
*/

    /**
     * add owner to database.
     *
     * @param username the owner for user
     */
    private void addOwner(final String username) {
        final ParseObject owner = new ParseObject(TABLE_OWNER);
        owner.put(COLUMN_USERNAME, username);
        try {
            owner.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter for bank accounts.
     *
     * @return returns list of bank accounts
     */
    public final ArrayList<BankAccount> getBankAccounts() {
        String username = ParseUser.getCurrentUser().getUsername();
        String[] references = getBankAccountReferences(username);
        ArrayList<BankAccount> list = null;
        if (references != null) {
            list = (ArrayList<BankAccount>) getBankAccountsHelper(references);
        }
        return list;
    }

    /**
     * gets bank account references.
     *
     * @param username user name for bank account
     * @return references of bank accounts
     */
    private String[] getBankAccountReferences(final String username) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_OWNER);
        query.whereEqualTo(COLUMN_USERNAME, username);
        JSONArray jsonReferences = null;
        try {
            jsonReferences = query.getFirst().getJSONArray(COLUMN_BANKACCOUNTS);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        String[] references = null;
        if (jsonReferences != null) {
            references = new String[jsonReferences.length()];
            try {
                for (int i = 0; i < references.length; i++) {
                    references[i] = jsonReferences.getString(i);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return references;
    }

    /**
     * getter of for the bank accounts helper.
     *
     * @param linkReferences the link references of accounts
     * @return list of bank account helpers
     */
    private List<BankAccount> getBankAccountsHelper(
    		final String[] linkReferences) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_BANKACCOUNT);
        query.whereContainedIn(COLUMN_ID, Arrays.asList(linkReferences));
        List<ParseObject> results = null;
        try {
            results = query.find();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        ArrayList<BankAccount> list = new ArrayList<BankAccount>();
        for (ParseObject result : results) {
            String objectId = result.getObjectId();
            String accountNumber = result.getString(COLUMN_ACCOUNTNUMBER);
            double balance = result.getNumber(COLUMN_BALANCE).doubleValue();
            String bankName = result.getString(COLUMN_BANKNAME);
            JSONArray references = result.getJSONArray(COLUMN_TRANSACTIONS);
            String[] transactions = null;
            if (references != null) {
                transactions = new String[references.length()];
                try {
                    for (int i = 0; i < transactions.length; i++) {
                        transactions[i] = references.getString(i);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            BankAccount account = new BankAccount(objectId,
            		accountNumber, balance,
                    bankName, transactions);
            list.add(account);
        }
        return list;
    }

    /**
     * creates new bank account.
     *
     * @param account the account to be created
     * @return boolean value of bank account creation
     */
    public final boolean createNewBankAccount(final BankAccount account) {
        boolean created = false;
        String username = ParseUser.getCurrentUser().getUsername();
        ParseObject owner = getOwner(username);
        if (owner != null) {
            String accountID = createAccount(account);
            if (accountID != null) {
                updateAccounts(owner, accountID);
                created = true;
            }
        }
        return created;
    }

    /**
     * getter for the owner of accounts.
     *
     * @param username the user name of account
     * @return returns the reference for the owner
     */
    private ParseObject getOwner(final String username) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_OWNER);
        query.whereEqualTo(COLUMN_USERNAME, username);
        ParseObject reference = null;
        try {
            reference = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reference;
    }



    /**
     * creates a bank account.
     *
     * @param account the bank account
     * @return the newly created account's object ID
     */
    private String createAccount(final BankAccount account) {
        ParseObject bankAccount = new ParseObject(TABLE_BANKACCOUNT);
        String objectId = null;
        String bankName = account.getBankName();
        String accountNumber = account.getAccountNumber();
        Number balance = account.getBalance();
        bankAccount.put(COLUMN_BANKNAME, bankName);
        bankAccount.put(COLUMN_ACCOUNTNUMBER, accountNumber);
        bankAccount.put(COLUMN_BALANCE, balance);
        JSONArray blankArray = new JSONArray();
        blankArray.put("");
        bankAccount.put(COLUMN_TRANSACTIONS, blankArray);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_BANKACCOUNT);
        query.whereEqualTo(COLUMN_BANKNAME, bankName);
        query.whereEqualTo(COLUMN_ACCOUNTNUMBER, accountNumber);
        ParseObject retrieved = null;
        try {
            retrieved = query.getFirst();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        if (retrieved == null) {
			try {
                bankAccount.save();
                objectId = bankAccount.getObjectId();
            } catch (ParseException e) {
                e.printStackTrace();
            }
		}
        return objectId;
    }

    /**
     * updates the accounts.
     *
     * @param owner owner of the account
     * @param accountID ID of the account of the owner
     */
    private void updateAccounts(final ParseObject owner,
    		final String accountID) {
        JSONArray array = owner.getJSONArray(COLUMN_BANKACCOUNTS);
        if (array == null) {
            array = new JSONArray();
        }
        array.put(accountID);
        owner.put(COLUMN_BANKACCOUNTS, array);
        try {
            owner.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * withdraws balance from account.
     *
     * @param account account to be withdrawn from
     * @param amount  amount of withdrawal
     * @param reason  reason of withdrawal
     * @return boolean value of success withdraw
     */
    public final boolean withdrawAmount(final BankAccount account,
    		final double amount, final String reason) {
        boolean deposited = false;
        if (amount <= account.getBalance()) {
            String transactionID = createWithdrawTransaction(amount, reason);
            updateBankAccountWithWithdraw(account, transactionID, amount);
            deposited = true;
        }
        return deposited;
    }

    /**
     * deposit balance to account.
     *
     * @param account account to be deposited to
     * @param amount  amount to deposit
     * @param reason  reason of deposit
     * @return boolean value of success deposit
     */
    public final boolean depositAmount(final BankAccount account,
    		final double amount, final String reason) {
        boolean deposited = false;
        String transactionID = createDepositTransaction(amount, reason);
        updateBankAccountWithDeposit(account, transactionID, amount);
        deposited = true;
        return deposited;
    }

    /**
     * creates the transaction of deposit.
     *
     * @param amount amount of transaction
     * @param reason reason of transaction
     * @return transaction ID
     */
    private String createDepositTransaction(final double amount,
    		final String reason) {
        ParseObject deposit = new ParseObject(TABLE_TRANSACTIONS);
        String transactionId = null;
        deposit.put(COLUMN_AMOUNT, amount);
        deposit.put(COLUMN_TRANSACTIONTYPE, "deposit");
        deposit.put(COLUMN_TRANSACTIONREASON, reason);
        try {
            deposit.save();
            transactionId = deposit.getObjectId();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transactionId;
    }

    /**
     * updates bank account with deposit.
     *
     * @param account account to be updated
     * @param transactionID the transaction ID
     * @param amount amount of transaction
     */
    private void updateBankAccountWithDeposit(final BankAccount account,
    		final String transactionID, final double amount) {
        double newBalance = account.getBalance() + amount;
        String accountID = account.getObjectId();
        JSONArray array = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_BANKACCOUNT);
        ParseObject result;
        try {
            result = query.get(accountID);
            array = result.getJSONArray(COLUMN_TRANSACTIONS);
            try {
                if (array.get(0).equals("")) {
                    array.put(0, transactionID);
                } else {
                    array.put(transactionID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result.put(COLUMN_BALANCE, newBalance);
            result.put(COLUMN_TRANSACTIONS, array);
            result.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * creates the transaction of withdrawal.
     *
     * @param amount amount of withdraw transaction
     * @param reason reason of withdrawal
     * @return transaction ID
     */
    private String createWithdrawTransaction(final double amount,
    		final String reason) {
        ParseObject deposit = new ParseObject(TABLE_TRANSACTIONS);
        String transactionId = null;
        deposit.put(COLUMN_AMOUNT, amount);
        deposit.put(COLUMN_TRANSACTIONTYPE, "withdraw");
        deposit.put(COLUMN_TRANSACTIONREASON, reason);
        try {
            deposit.save();
            transactionId = deposit.getObjectId();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transactionId;
    }

    /**
     * updates bank account with withdrawal.
     *
     * @param account account to be updated
     * @param transactionID the transaction ID
     * @param amount amount of withdrawal
     */
    private void updateBankAccountWithWithdraw(final BankAccount account,
    		final String transactionID, final double amount) {
        double newBalance = account.getBalance() - amount;
        String accountID = account.getObjectId();
        JSONArray array = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_BANKACCOUNT);
        ParseObject result;
        try {
            result = query.get(accountID);
            array = result.getJSONArray(COLUMN_TRANSACTIONS);
            try {
                if (array.get(0).equals("")) {
                    array.put(0, transactionID);
                } else {
                    array.put(transactionID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result.put(COLUMN_BALANCE, newBalance);
            result.put(COLUMN_TRANSACTIONS, array);
            result.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * object queried first.
     *
     * @param table table to query
     * @param key key for query
     * @param value value for query
     * @return result queried first
     */
    private ParseObject queryFirst(final String table,
    		final String key, final Object value) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(table);
        ParseObject result = null;
        query.whereEqualTo(key, value);
        try {
            result = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * getter for JSON Array.
     *
     * @param object object to be parsed
     * @param column column of array
     * @return array found
     */
    private JSONArray getJSONArray(final ParseObject object,
    		final String column) {
        JSONArray array = object.getJSONArray(column);
        try {
            if (array.get(0).equals("")) {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * gets transactions of the bank account.
     *
     * @param account account to retrieve transactions
     * @return array list of transactions
     */
    public final ArrayList<Transactions>
    getTransactions(final BankAccount account) {
        ParseObject result = queryFirst(TABLE_BANKACCOUNT,
        		COLUMN_ID, account.getObjectId());
        JSONArray transactions = getJSONArray(result, COLUMN_TRANSACTIONS);
        ArrayList<Transactions> list = new ArrayList<Transactions>();
        if (transactions != null) {
            for (int i = 0; i < transactions.length(); i++) {
                try {
                    list.add(retrieveTransaction(transactions.getString(i)));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * retrieve transactions.
     *
     * @param objectID objectID of transactions
     * @return transactions array
     */
    private Transactions retrieveTransaction(final String objectID) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_TRANSACTIONS);
        ParseObject result = null;
        Transactions t = null;
        try {
            result = query.get(objectID);
            t = new Transactions(result.getNumber(COLUMN_AMOUNT).doubleValue(),
                    result.getString(COLUMN_TRANSACTIONTYPE));
            t.setReason(result.getString(COLUMN_TRANSACTIONREASON));
            Date date = result.getCreatedAt();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            t.setDate(cal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * checks if user is already logged in.
     *
     * @return boolean value of log in
     */
    public final boolean isAlreadyLoggedIn() {
        return ParseUser.getCurrentUser() != null;
    }

    /**
     * gets report data of bank accounts.
     *
     * @return list of bank accounts
     */
    public final ArrayList<BankAccount> getReportData() {
        String username = ParseUser.getCurrentUser().getUsername();
        String[] references = getBankAccountReferences(username);
        ArrayList<BankAccount> list = null;
        if (references != null) {
            list = (ArrayList<BankAccount>) getBankAccountsHelper(references);
            for (BankAccount i : list) {
                ArrayList<Transactions> transactions = getTransactions(i);
                i.setListTrans(transactions);
            }
        }
        //displayReportData(list);
        return list;
    }

}
