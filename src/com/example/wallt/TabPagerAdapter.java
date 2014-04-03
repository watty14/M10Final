package com.example.wallt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
* Class that switches tabs.
*
* @author Thomas Harris (tharris7@gatech.edu)
* @version 1.0
*/
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * MAGIC_NUMBER - Instance Variable for int.
     */
    public static final int MAGIC_NUMBER = 3;

    /**
     * Constructor for the TabPagerAdapter.
     *
     * @param fm - FragmentManger object.
     */
    public TabPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public final Fragment getItem(final int i) {
        switch (i) {
            case 0:
                return new AccountFragment();
            case 1:
                return new ReportsFragment();
            case 2:
                return new SettingsFragment();
        default:
            break;
        }
        return null;
    }

    @Override
    public final int getCount() {
        return MAGIC_NUMBER;
    }
}
