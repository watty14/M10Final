package com.example.wallt;

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
import android.widget.EditText;
import android.widget.TextView;

public class ReportsFragment extends Fragment {
	
	private int[] checklistValues = new int[4];
	
	CheckBox spendingCheckBox;
	CheckBox incomeCheckBox;
	CheckBox cashflowCheckBox;
	CheckBox accountlistingCheckBox;
	DatePicker fromDate;
	DatePicker toDate;
	Button reportButton;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View windows = inflater.inflate(R.layout.fragment_reports, container, false);
		spendingCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxSpending);
		incomeCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxIncome);
		cashflowCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxCashFlow);
		accountlistingCheckBox = (CheckBox) windows.findViewById(R.id.checkAccountsListing);
		
		setUpIntroCheckBoxes();
		
		fromDate = (DatePicker) windows.findViewById(R.id.Datefrom);
		toDate = (DatePicker) windows.findViewById(R.id.Dateto);
		
		reportButton = (Button) windows.findViewById(R.id.buttonReport);
		setButtonOnClickListener();
		
		return windows;
	}

	private void setButtonOnClickListener() {
		reportButton.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				
			}
		});
	}

	private void setUpIntroCheckBoxes() {
		spendingCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checklistValues[0] = (spendingCheckBox.isChecked()) ? 1:0;
				if (spendingCheckBox.isChecked()) {
					cashflowCheckBox.setChecked(false);
					accountlistingCheckBox.setChecked(false);
					incomeCheckBox.setChecked(false);
				}
			}
			
		});
		
		incomeCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checklistValues[1] = (incomeCheckBox.isChecked()) ? 1:0;
				if (incomeCheckBox.isChecked()) {
					spendingCheckBox.setChecked(false);
					cashflowCheckBox.setChecked(false);
					accountlistingCheckBox.setChecked(false);
				}
			}
			
		});
		
		cashflowCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checklistValues[2] = (cashflowCheckBox.isChecked()) ? 1:0;
				if (cashflowCheckBox.isChecked()) {
					spendingCheckBox.setChecked(false);
					accountlistingCheckBox.setChecked(false);
					incomeCheckBox.setChecked(false);
				}
			}
			
		});
		
		accountlistingCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checklistValues[3] = (accountlistingCheckBox.isChecked()) ? 1:0;
				if (accountlistingCheckBox.isChecked()) {
					spendingCheckBox.setChecked(false);
					cashflowCheckBox.setChecked(false);
					incomeCheckBox.setChecked(false);
				}
			}
			
		});
		
	}
}
