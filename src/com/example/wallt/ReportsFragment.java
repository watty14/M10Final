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
	View windows;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		windows = inflater.inflate(R.layout.fragment_reports, container, false);
		spendingCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxSpending);
		incomeCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxIncome);
		cashflowCheckBox = (CheckBox) windows.findViewById(R.id.checkBoxCashFlow);
		accountlistingCheckBox = (CheckBox) windows.findViewById(R.id.checkAccountsListing);
		accountlistingCheckBox.setChecked(true);
		
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
				Intent i = new Intent(windows.getContext(), GeneratedReportActivity.class);
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
