package com.example.wallt;

import java.util.Calendar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 *
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class GeneratedReportActivity extends Activity {

    /**
     *
     */
    private TextView reportText;

    /**
     *
     */
    private int fromMonth;

    /**
     *
     */
    private int fromDay;

    /**
     *
     */
    private int fromYear;

    /**
     *
     */
    private int toMonth;

    /**
     *
     */
    private int toDay;

    /**
     *
     */
    private int toYear;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private Calendar from;

    /**
     *
     */
    private Calendar to;

    /**
     *
     */
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_report);
        reportText = (TextView) findViewById(R.id.reportText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar1);
        Bundle data = getIntent().getExtras();
        fromMonth = data.getInt("FROMMONTH");
        fromDay = data.getInt("FROMDAY");
        fromYear = data.getInt("FROMYEAR");
        toMonth = data.getInt("TOMONTH");
        toDay = data.getInt("TODAY");
        toYear = data.getInt("TOYEAR");
        type = data.getString("TYPE");
        from = Calendar.getInstance();
        to = Calendar.getInstance();
        from.set(fromYear, fromMonth, fromDay);
        to.set(toYear, toMonth, toDay);
        mProgressBar.setVisibility(View.VISIBLE);
        System.out.println("FROM " + from.toString());
        System.out.println("TO " + to.toString());
        new AsyncTaskGenerateReport().execute();
    }

    /**
     *
     *
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskGenerateReport extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            publishProgress();
            ReportsUtility reports = new ReportsUtility();
            if (type.equals("spending")) {
                return reports.generateSpendingReport(from, to);
            } else if (type.equals("income")) {
                return reports.generateIncomeReport(from, to);
            } else if (type.equals("cashflow")) {
                return reports.generateCashFlowReport(from, to);
            } else if (type.equals("accountlisting")) {
                return reports.generateAccountListingReport(from, to);
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            mProgressBar.setVisibility(View.INVISIBLE);
            reportText.setText(str);
        }
    }

}
