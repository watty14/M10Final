package com.example.wallt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Class that defines the add account activity.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class AddAccountActivity extends Activity {

    /**
     * Instance of the Bank Name Field.
     */
    private static EditText mBankNameField;

    /**
     * Instance of the Account Number field.
     */
    private static EditText mAccountNumber;

    /**
     * Instance of the Balance field.
     */
    private static EditText mBalanceField;

    /**
     * Instance of create account button.
     */
    private static Button mCreateAccount;

    /**
     * Instance of the progress bar.
     */
    private static ProgressBar mProgressBar;

    /**
     * LENGTH2: final instance variable of int.
     */
    public static final int LENGTH2 = 1000;

    /**
     * LENGTH3: final instance variable of int.
     */
    public static final int LENGTH3 = 100;

    /**
     * Empty string.
     */
    private static String empty = "";

    @Override
    protected final void onCreate(final Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_add_account);
        mBankNameField = (EditText) findViewById(R.id.bankname_field);
        mAccountNumber = (EditText) findViewById(R.id.accountnumber_field);
        mBalanceField = (EditText) findViewById(R.id.balance_field);
        mCreateAccount = (Button) findViewById(R.id.createaccount_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String bankName = (String)
                      mBankNameField.getText().toString();
                final String accountNumber =
                      mAccountNumber.getText().toString();
                final String balance = mBalanceField.getText().toString();

                if (empty.equals(bankName) || empty.equals(accountNumber)
                    || empty.equals(balance)) {
                    Toast.makeText(AddAccountActivity.this, "Invalid Input",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncTaskCreateBankAccount().execute(bankName,
                        accountNumber, balance);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mCreateAccount.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    /**
     * Inner class that updates accounts.
     *
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskCreateBankAccount
            extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(final String... params) {
            final BankAccount account = new BankAccount(null, params[1],
                    Integer.parseInt(params[2]), params[0], null);
            return ServerUtility.getInstance().createNewBankAccount(account);
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (result) {
                Toast.makeText(AddAccountActivity.this, "Account created",
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
            } else {
                mCreateAccount.setVisibility(View.VISIBLE);
                Toast.makeText(AddAccountActivity.this,
                        "Failed to create account! Try again.",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

}
