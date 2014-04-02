package com.example.wallt;

import com.parse.PushService;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * The MainActivity is the main page of this application.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class MainActivity extends FragmentActivity {

    /**
     * Instance variable for ViewPager.
     */
    private ViewPager tab;

    /**
     * Instance variable for TabPagerAdapter.
     */
    private TabPagerAdapter tabAdapter;

    /**
     * Instance variable for ActionBar.
     */
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushService.setDefaultPushCallback(this, MainActivity.class);
        setContentView(R.layout.activity_main);
        tabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tab = (ViewPager) findViewById(R.id.pager);
        tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);
                    }
                }
        );
        tab.setAdapter(tabAdapter);
        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabReselected(android.app.ActionBar.Tab ttb,
                FragmentTransaction ft) {
            }
            @Override
            public void onTabSelected(ActionBar.Tab ttb, FragmentTransaction ft) {
                tab.setCurrentItem(ttb.getPosition());
            }
            @Override
            public void onTabUnselected(android.app.ActionBar.Tab ttb,
                FragmentTransaction ft) {
            }
        };
      //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("ACCOUNTS").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("REPORTS").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("SETTINGS").setTabListener(tabListener));
    }
}
