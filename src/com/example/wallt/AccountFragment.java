package com.example.wallt;


import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Class that defines the behavior of the account fragment.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class AccountFragment extends ListFragment {

    /**
     * Instance of progress tracker.
     */
    private static ProgressBar mProgressBar;

    /**
     * Instance of refresh button.
     */
    private static Button mRefresh;

    /**
     * Instance of add account button.
     */
    private static Button mAddAccount;

    /**
     * List of accounts user for adapter.
     */
    private static List<BankAccount> accounts;

    /**
     * The view of this fragment.
     */
    private static View accountView;

    @Override
    public final View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstance) {
        accountView = inflater.inflate(R.layout.fragment_account,
                container, false);
        mProgressBar = (ProgressBar) accountView.findViewById(
                R.id.progressBar1);
        mRefresh = (Button) accountView.findViewById(R.id.refresh_button);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View clickView) {
                new AsyncTaskGetAccounts().execute();
            }
        });
        mAddAccount = (Button) accountView.findViewById(R.id.addAccount_button);
        mAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View clickView) {
                final Intent intent = new Intent(accountView.getContext(),
                    AddAccountActivity.class);
                startActivity(intent);
            }
        });
        return accountView;
    }

    @Override
    public final void onResume() {
        super.onResume();
        new AsyncTaskGetAccounts().execute();
    }

    @Override
    public final void onListItemClick(final ListView list,
            final View view, final int position, final long itemID) {
        final BankAccount desired = accounts.get(position);
        final Intent intent = new Intent(accountView.getContext(),
                TransactionActivity.class);
        intent.putExtra("account", desired);
        startActivity(intent);
    }

    /**
     * Class that performs update to bankaccount arraylist.
     *
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskGetAccounts extends
        AsyncTask<Void, Void, ArrayList<BankAccount>> {

        @Override
        protected ArrayList<BankAccount> doInBackground(final Void... params) {
            publishProgress();
            return ServerUtility.getInstance().getBankAccounts();
        }

        @Override
        protected void onProgressUpdate(final Void... params) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final ArrayList<BankAccount> list) {
            super.onPostExecute(list);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (list != null) {
                accounts = list;
                final ArrayAdapter<BankAccount> arrayAdapter =
                    new ArrayAdapter<BankAccount>(accountView.getContext(),
                            android.R.layout.simple_list_item_1, list);
                setListAdapter(arrayAdapter);
            }
        }
    }
}

