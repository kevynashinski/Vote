package com.ryletech.vote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import fr.ganfra.materialspinner.MaterialSpinner;

public class VoteActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialSpinner spinnerPresident,spinnerGovernor,spinnerSenator,spinnerWomenRep,spinnerMP,spinnerCounties;
    Button voteClear,submitVoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assignViews();

//        populate the counties


        spinnerCounties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!submitVoteButton.isEnabled()){
                    submitVoteButton.setEnabled(true);
                }

//            load the data
                StringRequest request=new StringRequest(Request.Method.POST, DATA_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                };

                AppController.getInstance().addToRequestQueue(request);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//disable the voting button
                if(submitVoteButton.isEnabled()) {
                    submitVoteButton.setEnabled(false);
                }
                }
        });
submitVoteButton.setOnClickListener(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void assignViews() {
        voteClear = (Button)findViewById(R.id.voteClearButton);
        submitVoteButton = (Button)findViewById(R.id.buttonSubmitVote);
        spinnerPresident=(MaterialSpinner)findViewById(R.id.spinnerPresident);
        spinnerGovernor=(MaterialSpinner)findViewById(R.id.spinnerGovernor);
        spinnerSenator=(MaterialSpinner)findViewById(R.id.spinnerSenator);
        spinnerWomenRep=(MaterialSpinner)findViewById(R.id.spinnerWomenRep);
        spinnerMP=(MaterialSpinner)findViewById(R.id.spinnerMP);
        spinnerCounties=(MaterialSpinner)findViewById(R.id.spinnerCounties);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSubmitVote:

                break;
        }
    }
}
