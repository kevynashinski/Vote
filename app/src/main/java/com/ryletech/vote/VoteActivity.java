package com.ryletech.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pierry.simpletoast.SimpleToast;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

import static com.ryletech.vote.AppConfig.COUNTIES_URL;
import static com.ryletech.vote.AppConfig.COUNTY_NAME;
import static com.ryletech.vote.AppConfig.GOVERNORS_URL;
import static com.ryletech.vote.AppConfig.ID_NUMBER;
import static com.ryletech.vote.AppConfig.PRESIDENTS_URL;
import static com.ryletech.vote.AppConfig.SENATORS_URL;
import static com.ryletech.vote.AppConfig.TAG;
import static com.ryletech.vote.AppConfig.WOMEN_REPS_URL;

public class VoteActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialSpinner spinnerPresident, spinnerGovernor, spinnerSenator, spinnerWomenRep, spinnerMP, spinnerCounties;
    Button voteClear, submitVoteButton;
    ProgressDialog progressDialog;
    private CoordinatorLayout votesCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        assignViews();

//        populate the counties
        populateCounties();

        populatePresidents();

        spinnerCounties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!submitVoteButton.isEnabled()) {
                    submitVoteButton.setEnabled(true);
                }

                final String county = String.valueOf(parent.getSelectedItem());

                if (new InternetConnection(getBaseContext()).isInternetAvailable()) {
//            load the data
                    StringRequest request = new StringRequest(Request.Method.POST, GOVERNORS_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "onResponse: response= " + response);

                            loadGovernors(county);

                            loadSenators(county);

                            loadWomenReps(county);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                    };

                    AppController.getInstance().addToRequestQueue(request);
                } else {
                    showWirelessSettings();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//disable the voting button
                if (submitVoteButton.isEnabled()) {
                    submitVoteButton.setEnabled(false);
                }
            }
        });

        submitVoteButton.setOnClickListener(this);
    }

    private void loadWomenReps(final String county) {

        if (new InternetConnection(getBaseContext()).isInternetAvailable()) {
            StringRequest request = new StringRequest(Request.Method.POST, WOMEN_REPS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: Response= " + response);

                    try {
                        parseWomenRepsResult(new JSONArray(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Error while converting string to json =" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(TAG, "onErrorResponse: Error = " + error.getMessage());

                    SimpleToast.error(getBaseContext(), "Server Error");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(COUNTY_NAME, county);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(request);
        } else {
//    show Wireless Settings
//            SimpleToast.warning(getBaseContext(),"Internet Connection Error");
            showWirelessSettings();
        }
    }

    private void parseWomenRepsResult(JSONArray jsonArray) {
        String[] womenReps = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                womenReps[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//
        ArrayAdapter<String> womenRepAdapter = new ArrayAdapter<>(VoteActivity.this, android.R.layout.simple_spinner_item, womenReps);
        womenRepAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWomenRep.setAdapter(womenRepAdapter);
    }

    private void loadSenators(final String county) {

        if (new InternetConnection(getBaseContext()).isInternetAvailable()) {
            StringRequest request = new StringRequest(Request.Method.POST, SENATORS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: Response= " + response);

                    try {
                        parseSenatorsResult(new JSONArray(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Error while converting string to json =" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(TAG, "onErrorResponse: Error = " + error.getMessage());

                    SimpleToast.error(getBaseContext(), "Server Error");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(COUNTY_NAME, county);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(request);
        } else {
//    show Wireless Settings
//            SimpleToast.warning(getBaseContext(),"Internet Connection Error");
            showWirelessSettings();
        }
    }

    private void parseSenatorsResult(JSONArray jsonArray) {
        String[] senators = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                senators[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> senatorsAdapter = new ArrayAdapter<>(VoteActivity.this, android.R.layout.simple_spinner_item, senators);
        senatorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSenator.setAdapter(senatorsAdapter);
    }

    private void loadGovernors(final String county) {

        if (new InternetConnection(getBaseContext()).isInternetAvailable()) {
            StringRequest request = new StringRequest(Request.Method.POST, GOVERNORS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: Response= " + response);

                    try {
                        parseGovernorsResult(new JSONArray(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Error while converting string to json =" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(TAG, "onErrorResponse: Error = " + error.getMessage());

                    SimpleToast.error(getBaseContext(), "Server Error");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(COUNTY_NAME, county);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(request);
        } else {
//    show Wireless Settings
//            SimpleToast.warning(getBaseContext(),"Internet Connection Error");
            showWirelessSettings();
        }
    }

    private void parseGovernorsResult(JSONArray jsonArray) {
        String[] governors = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                governors[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> governorsAdapter = new ArrayAdapter<>(VoteActivity.this, android.R.layout.simple_spinner_item, governors);
        governorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGovernor.setAdapter(governorsAdapter);
    }

    private void assignViews() {
        voteClear = (Button) findViewById(R.id.voteClearButton);
        submitVoteButton = (Button) findViewById(R.id.buttonSubmitVote);
        spinnerPresident = (MaterialSpinner) findViewById(R.id.spinnerPresident);
        spinnerGovernor = (MaterialSpinner) findViewById(R.id.spinnerGovernor);
        spinnerSenator = (MaterialSpinner) findViewById(R.id.spinnerSenator);
        spinnerWomenRep = (MaterialSpinner) findViewById(R.id.spinnerWomenRep);
//        spinnerMP = (MaterialSpinner) findViewById(R.id.spinnerMP);
        spinnerCounties = (MaterialSpinner) findViewById(R.id.spinnerCounties);
        votesCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.votesCoordinatorLayout);
    }

    private void populatePresidents() {
        if (new InternetConnection(getBaseContext()).isInternetAvailable()) {

            StringRequest request = new StringRequest(PRESIDENTS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: Response= " + response);

                    try {
                        parsePresidentsResult(new JSONArray(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Error while converting string to json =" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(TAG, "onErrorResponse: Error = " + error.getMessage());

                    SimpleToast.error(getBaseContext(), "Server Error");
                }
            });

            AppController.getInstance().addToRequestQueue(request);
        } else {
//    show Wireless Settings
//            SimpleToast.warning(getBaseContext(),"Internet Connection Error");
            showWirelessSettings();
        }
    }

    private void populateCounties() {

        if (new InternetConnection(getBaseContext()).isInternetAvailable()) {
            showProgress(true);

            StringRequest request = new StringRequest(COUNTIES_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: Response= " + response);
                    showProgress(false);

                    try {
                        parseCountiesResult(new JSONArray(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Error while converting string to json =" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);

                    Log.e(TAG, "onErrorResponse: Error = " + error.getMessage());

                    SimpleToast.error(getBaseContext(), "Server Error");
                }
            });

            AppController.getInstance().addToRequestQueue(request);
        } else {
//    show Wireless Settings
//            SimpleToast.warning(getBaseContext(),"Internet Connection Error");
            showWirelessSettings();
        }
    }

    private void parsePresidentsResult(JSONArray jsonArray) {

        String[] presidents = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                presidents[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> presidentsAdapter = new ArrayAdapter<>(VoteActivity.this, android.R.layout.simple_spinner_item, presidents);
        presidentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPresident.setAdapter(presidentsAdapter);
    }

    private void parseCountiesResult(JSONArray jsonArray) {

        String[] counties = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                counties[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, counties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCounties.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmitVote:

                new BottomSheet.Builder(this)
                        .setTitle("Id Number Confirmation")
                        .setMessage("Is this your id number: " + Prefs.getString(ID_NUMBER, ""))
                        .setPositiveButton("Confirm & Vote")
                        .setNegativeButton("Cancel")
                        .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info))
                        .setListener(new BottomSheetListener() {
                            @Override
                            public void onSheetShown(@NonNull BottomSheet bottomSheet) {

                            }

                            @Override
                            public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
                                if (menuItem.getActionView().getId() == DISMISS_EVENT_BUTTON_POSITIVE) {
                                    SimpleToast.info(getBaseContext(), "Ready to roll");
                                }
                            }

                            @Override
                            public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

                            }
                        })
                        .show();

//final String county,presidentName,senatorName,governorName,womanRepName;
//                county=spinnerCounties.getSelectedItem().toString();
//                presidentName=spinnerPresident.getSelectedItem().toString();
//                governorName = spinnerGovernor.getSelectedItem().toString();
//                senatorName = spinnerSenator.getSelectedItem().toString();
//                womanRepName = spinnerWomenRep.getSelectedItem().toString();
//
//                if (new InternetConnection(getBaseContext()).isInternetAvailable()) {
//                    StringRequest request = new StringRequest(Request.Method.POST,VOTE_URL, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.i(TAG, "onResponse: Response= " + response);
//
//                            try {
//                                parseSenatorsResult(new JSONArray(response));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Log.e(TAG, "onResponse: Error while converting string to json =" + e.getMessage());
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                            Log.e(TAG, "onErrorResponse: Error = " + error.getMessage());
//
//                            SimpleToast.error(getBaseContext(), "Server Error");
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> params = new HashMap<>();
//                            params.put(COUNTY_NAME, county);
//                            params.put(GOVERNOR_NAME, governorName);
//                            params.put(SENATOR_NAME, senatorName);
//                            params.put(PRESIDENT_NAME, presidentName);
//                            params.put(WOMAN_REP_NAME, womanRepName);
//                            return params;
//                        }
//                    };
//
//                    AppController.getInstance().addToRequestQueue(request);
//                } else {
////    show Wireless Settings
////            SimpleToast.warning(getBaseContext(),"Internet Connection Error");
//                    showWirelessSettings();
//                }
                break;
        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            progressDialog = new ProgressDialog(VoteActivity.this);
            progressDialog.setMessage("Fetching Data...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void showWirelessSettings() {
        Snackbar snackbar = Snackbar
                .make(votesCoordinatorLayout, "Wifi & Data Disabled!", Snackbar.LENGTH_LONG)
                .setAction("Enable", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                });
// Changing message text color
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
