package com.rechargeweb.rechargeweb.ReportsFragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rechargeweb.rechargeweb.Activity.ReportActivity;
import com.rechargeweb.rechargeweb.Adapters.DetailsAdapter;
import com.rechargeweb.rechargeweb.Model.RechargeDetails;
import com.rechargeweb.rechargeweb.R;
import com.rechargeweb.rechargeweb.ViewModels.AllReportViewModel;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeReportFragment extends Fragment implements DetailsAdapter.OnDetailsItemClickListener, DatePickerDialog.OnDateSetListener {


    private RecyclerView recyclerView;
    private String authKey;
    private AllReportViewModel allReportViewModel;
    private DetailsAdapter detailsAdapter;
    private ProgressBar loading;
    private TextView noRecordText;
    private String id;
    private ImageView fromImageView, toImageView;
    private TextView fromTextView, toTextView;

    private SimpleDateFormat simpleDateFormat;
    private String fromString, toString;
    private int dd, mm, yyyy;
    private boolean isFromDate, isTodate;
    OnRecharReportItemClickListener recharReportItemClickListener;

    private static final String TAG = RechargeReportFragment.class.getSimpleName();

    public RechargeReportFragment() {
        // Required empty public constructor
    }

    public interface OnRecharReportItemClickListener{
        void onRehcargeReportItmeClick(RechargeDetails details);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initializing authKey
        authKey = getResources().getString(R.string.auth_key);

        //Initializing ViewModel Class
        allReportViewModel = ViewModelProviders.of(this).get(AllReportViewModel.class);

        ReportActivity reportActivity = (ReportActivity) getActivity();
        if (reportActivity != null) {
            id = reportActivity.getSession_id();
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recharge_report, container, false);

        //Setting up from date and to date image and text view
        fromImageView = view.findViewById(R.id.report_fromdate_iv);
        toImageView = view.findViewById(R.id.report_todate_iv);

        fromTextView = view.findViewById(R.id.from_date_tv);
        toTextView = view.findViewById(R.id.todate_tv);

        //Initializing RecyclerView
        recyclerView = view.findViewById(R.id.nav_report_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (id != null) {
            getMobileRechargeReport();
        } else {
            Log.e(TAG, "Id is: " + id);
        }
        //Initializing progressbar and no record Text View
        loading = view.findViewById(R.id.report_loading_indicator);
        noRecordText = view.findViewById(R.id.no_record_text_recharge);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        toString = simpleDateFormat.format(calendar.getTime());
        fromTextView.setText(toString);
        toTextView.setText(toString);

        //Getting seperate values of year, month and day from the String today
        String[] part1 = toString.split("/");
        yyyy = Integer.parseInt(part1[0]);
        mm = Integer.parseInt(part1[1]) - 1;
        dd = Integer.parseInt(part1[2]);

        //On From ImageView Click
        fromImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                isTodate = false;
                showDate(yyyy, mm, dd, R.style.DatePickerSpinner);
            }
        });

        //On To ImageView Click
        toImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTodate = true;
                isFromDate = false;
                showDate(yyyy, mm, dd, R.style.DatePickerSpinner);
            }
        });
    }

    //Setting up the date picker
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(getContext())
                .callback(this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    @Override
    public void onDetailsItemClick(int position) {

        RechargeDetails rechargeDetails = detailsAdapter.getDetailsItem(position);
        recharReportItemClickListener.onRehcargeReportItmeClick(rechargeDetails);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

        if (isFromDate) {
            fromString = simpleDateFormat.format(calendar.getTime());
            fromTextView.setText(fromString);
            if (!fromString.isEmpty() && !toString.isEmpty()) {
                loading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                noRecordText.setVisibility(View.INVISIBLE);
                getRechargeListByDate(fromString, toString);
            }
        } else {
            toString = simpleDateFormat.format(calendar.getTime());
            toTextView.setText(toString);
            if (!fromString.isEmpty() && !toString.isEmpty()){
                loading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                noRecordText.setVisibility(View.INVISIBLE);
                getRechargeListByDate(fromString,toString);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        recharReportItemClickListener = (OnRecharReportItemClickListener)getActivity();
    }

    //Get recharge list
    private void getMobileRechargeReport() {
        Log.e(TAG, "Getting mobile recharge report");

        fromImageView.setEnabled(false);
        toImageView.setEnabled(false);
        allReportViewModel.getRechargeList(id, authKey).observe(this, new Observer<List<RechargeDetails>>() {
            @Override
            public void onChanged(List<RechargeDetails> rechargeDetails) {
                fromImageView.setEnabled(true);
                toImageView.setEnabled(true);
                if (rechargeDetails != null) {
                    Log.e(TAG, "recharge details is not null ");
                    for (int i = 0; i < rechargeDetails.size(); i++) {
                        RechargeDetails details = rechargeDetails.get(i);
                        if (details.getAmount().isEmpty()) {
                            Log.e(TAG, details.getNumber());
                            loading.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            noRecordText.setVisibility(View.VISIBLE);
                            noRecordText.setText(details.getApi_response());
                        } else {
                            loading.setVisibility(View.GONE);
                            Log.e(TAG, "Details list is full");
                            noRecordText.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            detailsAdapter = new DetailsAdapter(getContext(), RechargeReportFragment.this, rechargeDetails);
                            recyclerView.setAdapter(detailsAdapter);
                        }
                    }

                } else {
                    Log.e(TAG, "Details list is empty");
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    //Get recharge list by date
    private void getRechargeListByDate(String fromString, String toString) {
        noRecordText.setVisibility(View.GONE);

        fromImageView.setEnabled(false);
        toImageView.setEnabled(false);
        allReportViewModel.getRechargeListByDate(id, authKey, fromString, toString).observe(this, new Observer<List<RechargeDetails>>() {
            @Override
            public void onChanged(List<RechargeDetails> rechargeDetails) {
                fromImageView.setEnabled(true);
                toImageView.setEnabled(true);
                if (rechargeDetails != null) {
                    for (int i = 0; i < rechargeDetails.size(); i++) {
                        RechargeDetails details = rechargeDetails.get(i);
                        if (details.getAmount() == null || details.getAmount().isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            loading.setVisibility(View.INVISIBLE);
                            noRecordText.setVisibility(View.VISIBLE);
                            noRecordText.setText(details.getApi_response());
                        } else {
                            loading.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            noRecordText.setVisibility(View.GONE);
                            Log.e(TAG, "Details list by selectedDate is full");
                            detailsAdapter = new DetailsAdapter(getContext(), RechargeReportFragment.this, rechargeDetails);
                            recyclerView.setAdapter(detailsAdapter);
                        }
                    }

                } else {
                    Log.e(TAG, "Details list by selectedDate is empty");
                }
            }
        });
    }
}