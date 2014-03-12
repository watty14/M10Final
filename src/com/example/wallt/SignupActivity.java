package com.example.wallt;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class SignupActivity extends Activity {
	
	private EditText mUsernameField;
	private EditText mPasswordField;
	private Button mSignupButton;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		mUsernameField = (EditText) findViewById(R.id.username_field);
		mPasswordField = (EditText) findViewById(R.id.password_field);
		mSignupButton = (Button) findViewById(R.id.signup_button);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		mSignupButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				String username = mUsernameField.getText().toString();
				String password = mPasswordField.getText().toString();
				if (username.equals("") || password.equals("")) {
					Toast.makeText(SignupActivity.this, "Invalid Input", 
							Toast.LENGTH_SHORT).show();
				} else {
					new AsyncTaskLogInUser().execute(username, password);
					mSignupButton.setVisibility(View.INVISIBLE);
					mProgressBar.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	private class AsyncTaskLogInUser extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			return ServerUtility.signUpUser(params[0], params[1]);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				Intent i = new Intent(SignupActivity.this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
			} else {
				mProgressBar.setVisibility(View.INVISIBLE);
				mSignupButton.setVisibility(View.VISIBLE);
				mPasswordField.setText("");
				Toast.makeText(SignupActivity.this, "Sign up failed! Try again.", 
						Toast.LENGTH_LONG).show();
			}
		}
		
	}

}
