package com.example.wallt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;

/**
 * ReportsFragment is an activity for Report on
 * MainActivity.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class ReportsFragment extends Fragment {

    /**
     * checklistValues: Instance variable of Integer array.
     */
    private int[] checklistValues = new int[MAGIC_NUMBER2];

    /**
     * spendingCheckBox: Instance variable of CheckBox.
     */
    private CheckBox spendingCheckBox;

    /**
     * incomeCheckBox: Instance variable of CheckBox.
     */
    private CheckBox incomeCheckBox;

    /**
     * cashflowCheckBo: Instance variable of CheckBox.
     */
    private CheckBox cashflowCheckBox;

    /**
     * accountlistingCheckBox: Instance variable of CheckBox.
     */
    private CheckBox accountlistingCheckBox;

    /**
     * fromDate: Instance variable of DatePicker.
     */
    private DatePicker fromDate;

    /**
     * toDate: Instance variable of DatePicker.
     */
    private DatePicker toDate;

    /**
     * reportButton: Instance variable of Button.
     */
    private Button reportButton;

    /**
     * windows: Instance variable of View.
     */
    private View windows;

    /**
     * windows: Instance variable of View.
     */
    public static final int MAGIC_NUMBER = 3;

    /**
     * windows: Instance variable of View.
     */
    public static final int MAGIC_NUMBER2 = 4;

    @Override
    public final View onCreateView(final LayoutInflater inflater,
                 final ViewGroup container, final Bundle savedInstanceState) {
        windows = inflater.inflate(R.layout.fragment_reports, container, false);
        spendingCheckBox = (CheckBox) windows.findViewById(
                           R.id.checkBoxSpending);
        incomeCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxIncome);
        cashflowCheckBox = (CheckBox) windows.findViewById(
                           R.id.checkBoxCashFlow);
        accountlistingCheckBox = (CheckBox) windows.findViewById(
                                 R.id.checkAccountsListing);
        accountlistingCheckBox.setChecked(true);

        setUpIntroCheckBoxes();

        fromDate = (DatePicker) windows.findViewById(R.id.Datefrom);
        toDate = (DatePicker) windows.findViewById(R.id.Dateto);

        reportButton = (Button) windows.findViewById(R.id.buttonReport);
        setButtonOnClickListener();

        return windows;
    }

    /**
     * setButtonOnClickListener is a listener method for a button
     * for dates and checkboxes.
     */
    private void setButtonOnClickListener() {
        reportButton.setOnClickListener(new OnClickListener() {
            public void onClick(final View arg0) {
                int fromMonth = fromDate.getMonth();
                int fromDay = fromDate.getDayOfMonth();
                int fromYear = fromDate.getYear();
                int toMonth = toDate.getMonth();
                int toDay = toDate.getDayOfMonth();
                int toYear = toDate.getYear();
                String type = "";
                if (spendingCheckBox.isChecked()) {
                    type = "spending";
                } else if (accountlistingCheckBox.isChecked()) {
                    type = "accountlisting";
                } else if (cashflowCheckBox.isChecked()) {
                    type = "cashflow";
                } else if (incomeCheckBox.isChecked()) {
                    type = "income";
                }
                Intent i = new Intent(windows.getContext(),
                           GeneratedReportActivity.class);
                i.putExtra("FROMMONTH", fromMonth);
                i.putExtra("FROMDAY", fromDay);
                i.putExtra("FROMYEAR", fromYear);
                i.putExtra("TOMONTH", toMonth);
                i.putExtra("TODAY", toDay);
                i.putExtra("TOYEAR", toYear);
                i.putExtra("TYPE", type);
                startActivity(i);
            }
        });
    }

    /**
     * setUpIntroCheckBoxes helper method helps with
     * checking which checkbox has been.
     * pressed.
     */
    private void setUpIntroCheckBoxes() {
        spendingCheckBox.setOnCheckedChangeListener(
               new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(final CompoundButton buttonView,
                    final boolean isChecked) {
                // TODO Auto-generated method stub
                if (spendingCheckBox.isChecked()) {
                    checklistValues[0] = 1;
                } else {
                    checklistValues[0] = 0;
                }

                if (spendingCheckBox.isChecked()) {
                    cashflowCheckBox.setChecked(false);
                    accountlistingCheckBox.setChecked(false);
                    incomeCheckBox.setChecked(false);
                }
            }

        });

        incomeCheckBox.setOnCheckedChangeListener(
                new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(final CompoundButton buttonView,
                    final boolean isChecked) {
                // TODO Auto-generated method stub
                if (spendingCheckBox.isChecked()) {
                    checklistValues[1] = 1;
                } else {
                    checklistValues[1] = 0;
                }
                if (incomeCheckBox.isChecked()) {
                    spendingCheckBox.setChecked(false);
                    cashflowCheckBox.setChecked(false);
                    accountlistingCheckBox.setChecked(false);
                }
            }

        });

        cashflowCheckBox.setOnCheckedChangeListener(
                 new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(final CompoundButton buttonView,
                    final boolean isChecked) {
                // TODO Auto-generated method stub
                if (spendingCheckBox.isChecked()) {
                    checklistValues[2] = 1;
                } else {
                    checklistValues[2] = 0;
                }
                if (cashflowCheckBox.isChecked()) {
                    spendingCheckBox.setChecked(false);
                    accountlistingCheckBox.setChecked(false);
                    incomeCheckBox.setChecked(false);
                }
            }

        });

        accountlistingCheckBox.setOnCheckedChangeListener(
               new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(final CompoundButton buttonView,
                    final boolean isChecked) {
                // TODO Auto-generated method stub
                if (spendingCheckBox.isChecked()) {
                    checklistValues[MAGIC_NUMBER] = 1;
                } else {
                    checklistValues[MAGIC_NUMBER] = 0;
                }
                if (accountlistingCheckBox.isChecked()) {
                    spendingCheckBox.setChecked(false);
                    cashflowCheckBox.setChecked(false);
                    incomeCheckBox.setChecked(false);
                }
            }

        });

    }
}
