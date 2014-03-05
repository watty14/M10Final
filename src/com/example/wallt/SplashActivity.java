package com.example.wallt;

import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 1000) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					Intent i = null;
					if (ParseUser.getCurrentUser() != null) {
						i = new Intent(SplashActivity.this, MainActivity.class);
						
					} else {
						i = new Intent(SplashActivity.this, LoginOrSignUpActivity.class);
					}
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
				}
			}
		};
		splashThread.start();
	}
}
