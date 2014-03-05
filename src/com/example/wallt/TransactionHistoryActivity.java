package com.example.wallt;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class TransactionHistoryActivity extends ListActivity {
	
	private BankAccount account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle data = getIntent().getExtras();
		account = (BankAccount) data.getParcelable("account");
		new AsyncTaskGenerateHistory().execute();
	}
	
	private class AsyncTaskGenerateHistory extends AsyncTask<Void, Void, ArrayList<Transactions>> {

		@Override
		protected ArrayList<Transactions> doInBackground(Void... params) {
			return ServerUtility.getTransactions(account);
		}
		
		@Override
		protected void onPostExecute(ArrayList<Transactions> result) {
			super.onPostExecute(result);
			if (result != null) {
				ArrayAdapter<Transactions> arrayAdapter = new ArrayAdapter<Transactions>(
		    			TransactionHistoryActivity.this, android.R.layout.simple_list_item_1, result);
				setListAdapter(arrayAdapter);
			}
		}
		
	}

}
