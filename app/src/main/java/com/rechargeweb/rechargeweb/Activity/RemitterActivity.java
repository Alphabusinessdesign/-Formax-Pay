package com.rechargeweb.rechargeweb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rechargeweb.rechargeweb.Adapters.BeneficiaryAdapter;
import com.rechargeweb.rechargeweb.Constant.Constants;
import com.rechargeweb.rechargeweb.Model.AddBeneficiary;
import com.rechargeweb.rechargeweb.Model.Beneficiary;
import com.rechargeweb.rechargeweb.Model.Remitter;
import com.rechargeweb.rechargeweb.R;
import com.rechargeweb.rechargeweb.ViewModels.BeneficiaryViewModel;
import com.rechargeweb.rechargeweb.ViewModels.DmtViewModel;

import java.util.List;

public class RemitterActivity extends AppCompatActivity {

    TextView remitterName, remitterNumber, consumedLimit, remaninngLimit;
    RecyclerView recyclerView;

    private String auth;
    private String session_id;

    private String mobile;
    String userName;
    String remitter_id;

    private boolean isGetName;

    boolean getName;

    DmtViewModel dmtViewModel;
    BeneficiaryAdapter beneficiaryAdapter;

    AlertDialog dialog;
    FloatingActionButton addBeneficiaryButton;

    BeneficiaryViewModel beneficiaryViewModel;

    private static final String TAG = RemitterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remitter);

        auth = getResources().getString(R.string.auth_key);

        //Initializing ViewModel classes
        beneficiaryViewModel = ViewModelProviders.of(this).get(BeneficiaryViewModel.class);
        dmtViewModel = ViewModelProviders.of(this).get(DmtViewModel.class);

        remitterName = findViewById(R.id.remitter_name);
        remitterNumber = findViewById(R.id.remitter_phone);

        consumedLimit = findViewById(R.id.consumed_limit_tv);
        remaninngLimit = findViewById(R.id.remaining_limit_tv);

        addBeneficiaryButton = findViewById(R.id.add_beneficiary_fab);

        //Initializing recyclerView
        recyclerView = findViewById(R.id.remitter_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        beneficiaryAdapter = new BeneficiaryAdapter(this,auth);
        recyclerView.setAdapter(beneficiaryAdapter);

        //Get Intent
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.REMITTER_MOBILE)) {
            session_id = intent.getStringExtra(Constants.SESSION_ID);

            View layout = getLayoutInflater().inflate(R.layout.loading_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(layout);
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();

            mobile = intent.getStringExtra(Constants.REMITTER_MOBILE);
            getRemitterDetails(auth, mobile);
            getBeneficiaryList(auth, mobile);
        }


        //Add new beneficiary
        addBeneficiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeneficiary();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //Get remitter details
    private void getRemitterDetails(String auth, String number) {

        dmtViewModel.getRemitterDetails(auth, number).observe(RemitterActivity.this, new Observer<Remitter>() {
            @Override
            public void onChanged(Remitter remitter) {
                dialog.dismiss();
                if (remitter != null) {

                    Log.e(TAG, "Remitter details received");

                    remitter_id = remitter.getBeneficiary_id();
                    beneficiaryAdapter.setRemId(remitter_id);
                    String name = remitter.getName();
                    String parts[] = name.split("\\s+");
                    userName = parts[0];
                    String number = remitter.getMobile();
                    remitterName.setText(userName);
                    remitterNumber.setText(number);
                    String conLim = "Rs/- " + String.valueOf(remitter.getConsumed_limit());
                    String remLim = "Rs/- " + String.valueOf(remitter.getRemaining_limit());
                    consumedLimit.setText(conLim);
                    remaninngLimit.setText(remLim);
                } else {
                    Toast.makeText(getApplicationContext(), "Error Fetching Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Get beneficiary list
    private void getBeneficiaryList(String auth, String number) {
        dmtViewModel.getBeneficiaryDetails(auth, number).observe(RemitterActivity.this, new Observer<List<Beneficiary>>() {
            @Override
            public void onChanged(List<Beneficiary> beneficiaries) {
                if (beneficiaries != null) {
                    Log.e(TAG, "Beneficiary details received");
                    beneficiaryAdapter.setBeneficiaryList(beneficiaries);
                } else {
                    Toast.makeText(getApplicationContext(), "Error Fetching Beneficiary Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Add beneficiary
    private void addBeneficiary() {

        View layout = getLayoutInflater().inflate(R.layout.add_beneficiary, null);

        final TextInputLayout nameLayout = layout.findViewById(R.id.enter_name_ben_layout);
        final TextInputLayout ifscLayout = layout.findViewById(R.id.ifsc_code_ben_layout);
        final TextInputLayout accountLayout = layout.findViewById(R.id.account_number_ben_layout);
        final TextInputLayout confirmAccountLayout = layout.findViewById(R.id.confirm_account_ben_layout);

        final TextInputEditText name = layout.findViewById(R.id.enter_name_ben_text_input);
        final TextInputEditText ifsc = layout.findViewById(R.id.ifsc_code_text_input);
        final TextInputEditText account = layout.findViewById(R.id.account_number_input_text);
        final TextInputEditText confirmAccont = layout.findViewById(R.id.confirm_account_input_text);
        final ProgressBar progressBar = layout.findViewById(R.id.add_benificiary_loading);
        final TextView getNameTv = layout.findViewById(R.id.get_name_tv);

        final Button submitBeneficiaryButton = layout.findViewById(R.id.submit_beneficiary_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AddBeneficiaryDialog);
        builder.setView(layout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        //On Get Name button clicked
        getNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ifscS = ifsc.getText().toString().toUpperCase();
                String accountS = account.getText().toString();
                String conAccount = confirmAccont.getText().toString();

                if (ifscS.isEmpty()) {
                    ifscLayout.setError("Enter IFSC code");
                } else if (accountS.isEmpty()) {
                    accountLayout.setError("Enter Account Number");
                } else if (conAccount.isEmpty()) {
                    confirmAccountLayout.setError("Confirm Account Number");
                } else if (!accountS.equals(conAccount)) {
                    accountLayout.setError("Account Number doesn't match");
                    confirmAccountLayout.setError("Account Number doesn't match");
                } else {
                    getNameTv.setEnabled(false);
                    submitBeneficiaryButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    beneficiaryViewModel.validateAccount(session_id, auth, accountS, ifscS, mobile).observe(RemitterActivity.this, new Observer<AddBeneficiary>() {
                        @Override
                        public void onChanged(AddBeneficiary addBeneficiary) {
                            getNameTv.setEnabled(true);
                            submitBeneficiaryButton.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            if (addBeneficiary != null) {
                                Log.e(TAG,"Beneficiary name is Here");
                                String message = addBeneficiary.getMessage();
                                if (message.equals("Transaction Successful")) {
                                    String n = addBeneficiary.getStatus();

                                    if (!n.isEmpty()) {
                                        name.setText(n);
                                    }else {
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Log.e(TAG,"Beneficiary name is null");
                            }
                        }
                    });
                }

            }
        });

        //On Submit button click
        submitBeneficiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameS = name.getText().toString();
                String ifscS = ifsc.getText().toString().toUpperCase();
                String accountS = account.getText().toString();
                String conAccount = confirmAccont.getText().toString();

                if (ifscS.isEmpty()) {
                    ifscLayout.setError("Enter IFSC code");
                } else if (accountS.isEmpty()) {
                    accountLayout.setError("Enter Account Number");
                } else if (conAccount.isEmpty()) {
                    confirmAccountLayout.setError("Confirm Account Number");
                } else if (nameS.isEmpty()) {
                    nameLayout.setError("Enter Name");
                } else if (!accountS.equals(conAccount)) {
                    accountLayout.setError("Account Number doesn't match");
                    confirmAccountLayout.setError("Account Number doesn't match");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    submitBeneficiaryButton.setVisibility(View.GONE);

                    beneficiaryViewModel.addBeneficiary(auth, mobile, remitter_id, nameS, ifscS, accountS).observe(RemitterActivity.this, new Observer<AddBeneficiary>() {
                        @Override
                        public void onChanged(AddBeneficiary addBeneficiary) {
                            progressBar.setVisibility(View.GONE);
                            submitBeneficiaryButton.setVisibility(View.VISIBLE);
                            if (addBeneficiary != null) {

                                Log.e(TAG, "Beneficiary is not null");
                                String message = addBeneficiary.getMessage();
                                if (message.equals("Transaction Successful")) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }else {
                                Log.e(TAG,"Beneficiary not added");
                            }
                        }
                    });
                }
            }
        });
    }

}
