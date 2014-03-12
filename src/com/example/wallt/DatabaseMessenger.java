/*

package com.example.wallt;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DatabaseMessenger {

	//Table names
	public static final String TABLE_USER = "User";
	public static final String TABLE_OWNER = "AccountOwners";
	public static final String TABLE_BANKACCOUNT = "BankAccount";
	public static final String TABLE_TRANSACTIONS = "Transactions";

	//Column names
	public static final String COLUMN_ID = "objectId";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_BANKACCOUNTS = "bankaccounts";
	public static final String COLUMN_BANKNAME = "bankname";
	public static final String COLUMN_ACCOUNTNUMBER = "accountnumber";
	public static final String COLUMN_BALANCE = "balance";
	public static final String COLUMN_TRANSACTIONS = "transactions";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_TRANSACTIONTYPE = "transactiontype";
	
	public static boolean logInUser(String username, String password) {
		ParseUser user = null;
		try {
			user = ParseUser.logIn(username, password);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user != null;
	}
	
	public static boolean signUpUser(String username, String password) {
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(username);
		boolean isSignedUp = false;
		try {
			user.signUp();
			addOwner(username);
			isSignedUp = true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isSignedUp;
	}

	private static void addOwner(String username) {
		ParseObject owner = new ParseObject(TABLE_OWNER);
		owner.put(COLUMN_USERNAME, username);
		try {
			owner.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static JSONArray getBankAccount() {
		String username = ParseUser.getCurrentUser().getUsername();
		return getBankAccountReferences(username);
	}

	private static String[] getBankAccountReferences(String username) {
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

	private static List<BankAccount> getBankAccountsHelper(String[] linkReferences) {
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
			BankAccount account = new BankAccount(objectId, accountNumber, balance,
					bankName, transactions);
			list.add(account);
		}
		return list;
	}

	public static boolean createNewBankAccount(BankAccount account) {
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

	private static ParseObject getOwner(String username) {
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


	private static String createAccount(BankAccount account) {
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
		if (retrieved == null)
			try {
				bankAccount.save();
				objectId = bankAccount.getObjectId();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return objectId;
	}

	private static void updateAccounts(ParseObject owner, String accountID) {
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

	public static boolean withdrawAmount(BankAccount account, double amount) {
		boolean deposited = false;
		if (amount <= account.getBalance()) {
			String transactionID = createWithdrawTransaction(amount);
			updateBankAccountWithWithdraw(account, transactionID, amount);
			deposited = true;
		}
		return deposited;
	}

	public static boolean depositAmount(BankAccount account, double amount) {
		boolean deposited = false;
		String transactionID = createDepositTransaction(amount);
		updateBankAccountWithDeposit(account, transactionID, amount);
		deposited = true;
		return deposited;
	}

	private static String createDepositTransaction(double amount) {
		ParseObject deposit = new ParseObject(TABLE_TRANSACTIONS);
		String transactionId = null;
		deposit.put(COLUMN_AMOUNT, amount);
		deposit.put(COLUMN_TRANSACTIONTYPE, "deposit");
		try {
			deposit.save();
			transactionId = deposit.getObjectId();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return transactionId;
	}

	private static void updateBankAccountWithDeposit(BankAccount account,
			String transactionID, double amount) {
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

	private static String createWithdrawTransaction(double amount) {
		ParseObject deposit = new ParseObject(TABLE_TRANSACTIONS);
		String transactionId = null;
		deposit.put(COLUMN_AMOUNT, amount);
		deposit.put(COLUMN_TRANSACTIONTYPE, "withdraw");
		try {
			deposit.save();
			transactionId = deposit.getObjectId();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return transactionId;
	}

	private static void updateBankAccountWithWithdraw(BankAccount account,
			String transactionID, double amount) {
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

	public static List<Transactions> getTransactions(BankAccount account) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_BANKACCOUNT);
		ParseObject result = null;
		try {
			result = query.get(account.getObjectId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray transactions = result.getJSONArray(COLUMN_TRANSACTIONS);
		try {
			if (transactions.get(0).equals("")) {
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayList<Transactions> list = new ArrayList<Transactions>();
		for (int i = 0; i < transactions.length(); i++) {
			try {
				list.add(retrieveTransaction(transactions.getString(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	private static Transactions retrieveTransaction(String objectID) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_TRANSACTIONS);
		ParseObject result = null;
		Transactions t = null;
		try {
			result = query.get(objectID);
			t = new Transactions(result.getNumber(COLUMN_AMOUNT).doubleValue(),
					result.getString(COLUMN_TRANSACTIONTYPE));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public static boolean isAlreadyLoggedIn() {
		return ParseUser.getCurrentUser() != null;
	}


}

*/