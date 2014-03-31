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
 *
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class AddAccountActivity extends Activity {

    /**
     *
     */
    private EditText mBankNameField;

    /**
     *
     */
    private EditText mAccountNumberField;

    /**
     *
     */
    private EditText mBalanceField;

    /**
     *
     */
    private Button mCreateAccountButton;

    /**
     *
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
                if (bankName.equals("") || accountNumber.equals("") || balance.equals("")) {
                    Toast.makeText(AddAccountActivity.this, "Invalid Input",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncTaskCreateBankAccount().execute(bankName, accountNumber, balance);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mCreateAccountButton.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    /**
     *
     *
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskCreateBankAccount extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            BankAccount account = new BankAccount(null, params[1], Integer.parseInt(params[2]),
                                                      params[0], null);
            return ServerUtility.createNewBankAccount(account);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (result) {
                Toast.makeText(AddAccountActivity.this, "Account created",
                        Toast.LENGTH_LONG).show();
                for (int waitx = 0; waitx < 5000; waitx++) {
                    for (int waity = 0; waity < 5000; waity++) {
                    }
                }
                finish();
            } else {
                mCreateAccountButton.setVisibility(View.VISIBLE);
                Toast.makeText(AddAccountActivity.this, "Failed to create account! Try again.",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

}
