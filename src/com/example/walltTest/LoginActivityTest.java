package com.example.walltTest;

import android.app.Activity;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.wallt.LoginActivity;
import com.example.wallt.R;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.Assert;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	
	private String username;
	private String password;
	private EditText mUsernameField;
	private EditText mPasswordField;
	private Activity mActivity;
	
	public LoginActivityTest() {
		super(LoginActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void setUp() throws Exception {
			super.setUp();
			mActivity = getActivity();
			
		username = "";
		password = "";
//		 mUsernameField = (EditText) mActivity.findViewById(R.id.username_field);
//	     mPasswordField = (EditText) mActivity.findViewById(R.id.password_field);
	}
	
	@Test
	public void correctUsernameAndPasswordTest() {
		assertTrue(mUsernameField.getText().toString()==null);
		mUsernameField.setText("admin");
		mPasswordField.setText("pass123");
		assertTrue(mUsernameField.getText().toString().equals("admin")
					&& mPasswordField.getText().toString().equals("pass123") );}


}
