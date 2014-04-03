package com.example.wallt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * LoginOrSignUpActivity is an activity for login on
 * MainActivity.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class LoginOrSignUpActivity extends Activity {

    /**
     * mLoginButton: Instance variable of Button.
     */
    private Button mLoginButton;

    /**
     * mSignupButton: Instance variable of Button.
     */
    private Button mSignupButton;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sign_up);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mSignupButton = (Button) findViewById(R.id.signup_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(
                             LoginOrSignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(
                             LoginOrSignUpActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * getmLoginButton getter method.
     *
     * @return Button - mLoginButton.
     */
    public final Button getmLoginButton() {
        return mLoginButton;
    }

   /**
    * getmSignupButton getter method.
    *
    * @return Button - mSignupButton.
    */
    public final Button getmSignupButton() {
         return mSignupButton;
    }
}
