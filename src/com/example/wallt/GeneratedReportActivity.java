package com.example.wallt;

import java.util.Calendar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Class describing the behavior of generated report activity.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class GeneratedReportActivity extends Activity {

    /**
     * Text view for report.
     */
    private static TextView reportText;

    /**
     * month to check from.
     */
    private static int fromMonth;

    /**
     * Day to check from.
     */
    private static int fromDay;

    /**
     * Year to check from.
     */
    private static int fromYear;

    /**
     * Month to check to.
     */
    private static int toMonth;

    /**
     * Day to check to.
     */
    private static int toDay;

    /**
     * Year to check to.
     */
    private static int toYear;

    /**
     * Type of report.
     */
    private static String type;

    /**
     * Calendar object from.
     */
    private static Calendar from;

    /**
     * Calendar object to.
     */
    private static Calendar to;

    /**
     * Progressbar that is a tracker.
     */
    private static ProgressBar mProgressBar;

    @Override
    protected final void onCreate(final Bundle savedInstance) {
        super.onCreate(savedInstance);
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
        new AsyncTaskGenerateReport().execute();
    }

    /**
     * Inner class that generates report.
     *
     * @author Thomas Harris (tharris7@gatech.edu)
     * @version 1.0
     */
    private class AsyncTaskGenerateReport
            extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(final Void... params) {
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
        protected void onProgressUpdate(final Void... params) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final String str) {
            super.onPostExecute(str);
            mProgressBar.setVisibility(View.INVISIBLE);
            reportText.setText(str);
        }
    }

}
