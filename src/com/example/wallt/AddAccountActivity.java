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
    private EditText mBankNameField;

    /**
     * Instance of the Account Number field.
     */
    private EditText mAccountNumberField;

    /**
     * Instance of the Balance field.
     */
    private EditText mBalanceField;

    /**
     * Instance of create account button.
     */
    private Button mCreateAccountButton;

    /**
     * Instance of the progress bar.
     */
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        mBankNameField = (EditText) findViewById(R.id.bankname_field);
        mAccountNumberField = (EditText) findViewById(R.id.accountnumber_field);
        mBalanceField = (EditText) findViewById(R.id.balance_field);
        mCreateAccountButton = (Button) findViewById(R.id.createaccount_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bankName = mBankNameField.getText().toString();
                String accountNumber = mAccountNumberField.getText().toString();
                String balance = mBalanceField.getText().toString();
                if (bankName.equals("") || accountNumber.equals("")
                    || balance.equals("")) {
                    Toast.makeText(AddAccountActivity.this, "Invalid Input",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncTaskCreateBankAccount().execute(bankName,
                        accountNumber, balance);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mCreateAccountButton.setVisibility(View.INVISIBLE);
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
        protected Boolean doInBackground(String... params) {
            BankAccount account = new BankAccount(null, params[1],
                    Integer.parseInt(params[2]), params[0], null);
            return ServerUtility.createNewBankAccount(account);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (result) {
                Toast.makeText(AddAccountActivity.this, "Account created",
                        Toast.LENGTH_LONG).show();
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int waited = 0;
                            while (waited < 1000) {
                                sleep(100);
                                waited += 100;
                            }
                        } catch (InterruptedException e) {
                            //do nothing
                        } finally {
                            finish();
                        }
                    }
                };
                th.start();
            } else {
                mCreateAccountButton.setVisibility(View.VISIBLE);
                Toast.makeText(AddAccountActivity.this,
                        "Failed to create account! Try again.",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

}
