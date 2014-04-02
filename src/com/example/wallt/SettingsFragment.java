package com.example.wallt;

import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * SettingFragment is a part of MainActivity which takes care of logout.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class SettingsFragment extends ListFragment {

    /**
     * mLogout : Instance Variable for a button.
     */
    private Button mLogout;

    /**
     * account : Instance Variable for a View.
     */
    private View account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        account = inflater.inflate(R.layout.fragment_settings, container, false);
        mLogout = (Button) account.findViewById(R.id.logout_button);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent intents = new Intent(account.getContext(), LoginOrSignUpActivity.class);
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intents);
            }
        });
        return account;
    }
}
