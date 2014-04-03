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

/**
 * Transaction Activity Class.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class TransactionActivity extends Activity {

    /**
     * account: instance variable of BankAccount.
     */
    private BankAccount account;

    /**
     * mBankname: instance variable of TextView.
     */
    private TextView mBankname;

    /**
     * mAccountnumber: instance variable of TextView.
     */
    private TextView mAccountnumber;

    /**
     * mBalance: instance variable of TextView.
     */
    private TextView mBalance;

    /**
     * mDeposit: instance variable of Button.
     */
    private Button mDeposit;

    /**
     * mWithdraw: instance variable of Button.
     */
    private Button mWithdraw;

    /**
     * mTransactionHistory: instance variable of Button.
     */
    private Button mTransactionHistory;

    /**
     * mAmount: instance variable of EditText.
     */
    private EditText mAmount;

    /**
     * mReason: instance variable of EditText.
     */
    private EditText mReason;

    /**
     * mProgressBar: instance variable of ProgressBar.
     */
    private ProgressBar mProgressBar;

    /**
     * LENGTH: final instance variable of int.
     */
    public static final int LENGTH = 5000;

    /**
     * LENGTH2: final instance variable of int.
     */
    public static final int LENGTH2 = 1000;

    /**
     * LENGTH3: final instance variable of int.
     */
    public static final int LENGTH3 = 100;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
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
        mTransactionHistory = (Button) findViewById(
                        R.id.transactionhistory_button);
        mAmount = (EditText) findViewById(R.id.amount_field);
        mReason = (EditText) findViewById(R.id.reason_field);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

        mBankname.setText("Bank Name: " + account.getBankName());
        mAccountnumber.setText("Account Number: " + account.getAccountNumber());
        mBalance.setText("Balance: " + account.getBalance());

        mDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                mDeposit.setVisibility(View.INVISIBLE);
                mWithdraw.setVisibility(View.INVISIBLE);
                new AsyncTaskDeposit().execute();
            }
        });

        mWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                mDeposit.setVisibility(View.INVISIBLE);
                mWithdraw.setVisibility(View.INVISIBLE);
                new AsyncTaskWithdraw().execute();
            }
        });

        mTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(TransactionActivity.this,
                                      TransactionHistoryActivity.class);
                i.putExtra("account", account);
                startActivity(i);
            }
        });

    }

    /**
     * AsyncTaskDeposit class.
     * updates deposits
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskDeposit extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(final Void... params) {
            String amountStr = mAmount.getText().toString();
            String reasonStr = mReason.getText().toString();
            if (amountStr.equals("")) {
                return false;
            }
            double amount = Integer.parseInt(amountStr);
            return ServerUtility.getInstance().depositAmount(account, amount, reasonStr);
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (result) {
                Toast.makeText(TransactionActivity.this, "Deposit successful",
                        Toast.LENGTH_LONG).show();
                final Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int waited = 0;
                            while (waited < LENGTH2) {
                                sleep(LENGTH3);
                                waited += LENGTH3;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //do nothing
                        } finally {
                            finish();
                        }
                    }
                };
                thread.start();
                finish();
            } else {
                mDeposit.setVisibility(View.VISIBLE);
                mWithdraw.setVisibility(View.VISIBLE);
                Toast.makeText(TransactionActivity.this, "Failed to"
                        + " desposit money! Try again.",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * AsyncTaskWithdraw class.
     * updates withdrawals.
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskWithdraw extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(final Void... params) {
            String amountStr = mAmount.getText().toString();
            String reasonStr = mReason.getText().toString();
            if (amountStr.equals("")) {
                return false;
            }
            double amount = Integer.parseInt(amountStr);
            return ServerUtility.getInstance().withdrawAmount(account, amount, reasonStr);
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (result) {
                Toast.makeText(TransactionActivity.this, "Withdrawal s"
                       + "uccessful", Toast.LENGTH_LONG).show();
                final Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int waited = 0;
                            while (waited < LENGTH2) {
                                sleep(LENGTH3);
                                waited += LENGTH3;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //do nothing
                        } finally {
                            finish();
                        }
                    }
                };
                thread.start();
                finish();
            } else {
                mDeposit.setVisibility(View.VISIBLE);
                mWithdraw.setVisibility(View.VISIBLE);
                Toast.makeText(TransactionActivity.this, "Failed to withdraw"
                        + "money! Try again.",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

}
