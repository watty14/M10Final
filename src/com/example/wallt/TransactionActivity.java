package com.example.wallt;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class TransactionActivity extends Activity {

	private BankAccount account;
	private TextView mBankname;
	private TextView mAccountnumber;
	private TextView mBalance;
	private Button mDeposit;
	private Button mWithdraw;
	private Button mTransactionHistory;
	private EditText mAmount;
	private ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);
		Bundle data = getIntent().getExtras();
		account = (BankAccount) data.getParcelable("account");
		mBankname = (TextView) findViewById(R.id.bankname_text);
		mAccountnumber = (TextView) findViewById(R.id.accountnumber_text);
		mBalance = (TextView) findViewById(R.id.balance_text);
		System.out.println("hello");
		mDeposit = (Button) findViewById(R.id.deposit_button);
		mWithdraw = (Button) findViewById(R.id.withdraw_button);
		mTransactionHistory = (Button) findViewById(R.id.transactionhistory_button);
		mAmount = (EditText) findViewById(R.id.amount_field);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		mBankname.setText("Bank Name: " + account.getBankName());
		mAccountnumber.setText("Account Number: " + account.getAccountNumber());
		mBalance.setText("Balance: " + account.getBalance());
		
		mDeposit.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressBar.setVisibility(View.VISIBLE);
				mDeposit.setVisibility(View.INVISIBLE);
				mWithdraw.setVisibility(View.INVISIBLE);
				new AsyncTaskDeposit().execute();
			}
		});
		
		mWithdraw.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressBar.setVisibility(View.VISIBLE);
				mDeposit.setVisibility(View.INVISIBLE);
				mWithdraw.setVisibility(View.INVISIBLE);
				new AsyncTaskWithdraw().execute();
			}
		});
		
		mTransactionHistory.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(TransactionActivity.this, TransactionHistoryActivity.class);
			    i.putExtra("account", account);
				startActivity(i);
			}
		});
		
	}
	
	private class AsyncTaskDeposit extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			String amountStr = mAmount.getText().toString();
			if (amountStr.equals("")) {
				return false;
			}
			double amount = Integer.parseInt(amountStr);
			return ServerUtility.depositAmount(account, amount);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.INVISIBLE);
			if (result) {
				Toast.makeText(TransactionActivity.this, "Deposit successful", 
						Toast.LENGTH_LONG).show();
				for (int waitx = 0; waitx < 5000; waitx++) {
					for (int waity = 0; waity < 5000; waity++) {
					}
				}
				finish();
			} else {
				mDeposit.setVisibility(View.VISIBLE);
				mWithdraw.setVisibility(View.VISIBLE);
				Toast.makeText(TransactionActivity.this, "Failed to desposit money! Try again.", 
						Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	private class AsyncTaskWithdraw extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			String amountStr = mAmount.getText().toString();
			if (amountStr.equals("")) {
				return false;
			}
			double amount = Integer.parseInt(amountStr);;
			return ServerUtility.withdrawAmount(account, amount);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.INVISIBLE);
			if (result) {
				Toast.makeText(TransactionActivity.this, "Withdrawal successful", 
						Toast.LENGTH_LONG).show();
				for (int waitx = 0; waitx < 5000; waitx++) {
					for (int waity = 0; waity < 5000; waity++) {
					}
				}
				finish();
			} else {
				mDeposit.setVisibility(View.VISIBLE);
				mWithdraw.setVisibility(View.VISIBLE);
				Toast.makeText(TransactionActivity.this, "Failed to withdraw money! Try again.", 
						Toast.LENGTH_LONG).show();
			}
		}
		
	}

}
