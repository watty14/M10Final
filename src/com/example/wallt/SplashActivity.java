package com.example.wallt;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

/**
 * SplashActivity is an activity for setting time on
 * MainActivity.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class SplashActivity extends Activity {

    /**
     * LENGTH2: final instance variable of int.
     */
    public static final int LENGTH2 = 1000;

    /**
     * LENGTH3: final instance variable of int.
     */
    public static final int LENGTH3 = 100;
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < LENGTH2) {
                        sleep(LENGTH3);
                        waited += LENGTH3;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = null;
                    if (ServerUtility.getInstance().isAlreadyLoggedIn()) {
                        i = new Intent(SplashActivity.this, MainActivity.class);

                    } else {
                        i = new Intent(SplashActivity.this,
                            LoginOrSignUpActivity.class);
                    }
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                             | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
        splashThread.start();
    }
}
