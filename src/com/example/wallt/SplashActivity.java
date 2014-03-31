package com.example.wallt;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

/**
 *
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
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
                    if (ServerUtility.isAlreadyLoggedIn()) {
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
