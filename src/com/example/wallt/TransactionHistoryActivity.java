package com.example.wallt;

import java.util.ArrayList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;

/**
 * Transaction History Activity Class.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class TransactionHistoryActivity extends ListActivity {

    /**
     * account: instance variable of BankAccount.
     */
    private BankAccount account;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        account = (BankAccount) data.getParcelable("account");
        new AsyncTaskGenerateHistory().execute();
    }

    /**
     * AsyncTaskGenerateHistory Class.
     * generates history to transaction arrayList.
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskGenerateHistory extends
              AsyncTask<Void, Void, ArrayList<Transactions>> {

        @Override
        protected ArrayList<Transactions> doInBackground(final Void... params) {
            return ServerUtility.getInstance().getTransactions(account);
        }

        @Override
        protected void onPostExecute(final ArrayList<Transactions> result) {
            super.onPostExecute(result);
            if (result != null) {
                ArrayAdapter<Transactions> arrayAdapter =
                new ArrayAdapter<Transactions>(TransactionHistoryActivity.this,
                               android.R.layout.simple_list_item_1, result);
                setListAdapter(arrayAdapter);
            }
        }

    }

}
