package com.example.wallt;


import java.util.ArrayList;

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
    private ProgressBar mProgressBar;

    /**
     * Instance of refresh button.
     */
    private Button mRefresh;

    /**
     * Instance of add account button.
     */
    private Button mAddAccount;

    /**
     * List of accounts user for adapter.
     */
    private ArrayList<BankAccount> accounts;

    /**
     * The view of this fragment.
     */
    private View accountView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        accountView = inflater.inflate(R.layout.fragment_account,
                container, false);
        mProgressBar = (ProgressBar) accountView.findViewById(
                R.id.progressBar1);
        mRefresh = (Button) accountView.findViewById(R.id.refresh_button);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskGetAccounts().execute();
            }
        });
        mAddAccount = (Button) accountView.findViewById(R.id.addAccount_button);
        mAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(accountView.getContext(),
                    AddAccountActivity.class);
                startActivity(i);
            }
        });
        return accountView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncTaskGetAccounts().execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        BankAccount desired = accounts.get(position);
        Intent i = new Intent(accountView.getContext(),
                TransactionActivity.class);
        i.putExtra("account", desired);
        startActivity(i);
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
        protected ArrayList<BankAccount> doInBackground(Void... params) {
            publishProgress();
            return ServerUtility.getBankAccounts();
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<BankAccount> list) {
            super.onPostExecute(list);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (list != null) {
                accounts = list;
                ArrayAdapter<BankAccount> arrayAdapter =
                    new ArrayAdapter<BankAccount>(accountView.getContext(),
                            android.R.layout.simple_list_item_1, list);
                setListAdapter(arrayAdapter);
            }
        }
    }
}

