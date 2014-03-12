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
import android.widget.EditText;
import android.widget.TextView;

public class ReportsFragment extends Fragment {
	
	private int[] checklistValues = new int[2];
	
	CheckBox spendingCheckBox;
	CheckBox incomeCheckBox;
	CheckBox budgetCheckBox;
	
	Button reportButton;
	
	EditText dateFrom;
	EditText dateTo;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View windows = inflater.inflate(R.layout.fragment_reports, container, false);
		spendingCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxSpending);
		incomeCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxIncome);
		budgetCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxBudget);
		
		setUpIntroCheckBoxes();
		
		dateFrom = (EditText) windows.findViewById(R.id.editTextDate1);
		dateTo = (EditText) windows.findViewById(R.id.editTextDate2);
		
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
			}
			
		});
		
		incomeCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checklistValues[1] = (incomeCheckBox.isChecked()) ? 1:0;
			}
			
		});
		
		budgetCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checklistValues[1] = (budgetCheckBox.isChecked()) ? 1:0;
			}
			
		});
		// TODO Auto-generated method stub
		
	}
}
