package com.ryletech.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pierry.simpletoast.SimpleToast;

import fr.ganfra.materialspinner.MaterialSpinner;

import static com.ryletech.vote.AppConfig.*;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    EditText createAccountPassword;
    EditText createAccountEmailAddress;
    EditText createAccountIdNumber;
    Button createAccountClear,createAccountRegister;
    MaterialSpinner createAccountGender;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assignViews();

        populateGender();

createAccountClear.setOnClickListener(this);
        createAccountRegister.setOnClickListener(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void populateGender() {

    }

    private void assignViews() {
        createAccountIdNumber = (EditText)findViewById(R.id.createAccountIdNumber);
        createAccountEmailAddress = (EditText)findViewById(R.id.createAccountEmailAddress);
        createAccountGender = (MaterialSpinner) findViewById(R.id.createAccountGender);
        createAccountPassword = (EditText)findViewById(R.id.createAccountPassword);
        createAccountGender = (MaterialSpinner)findViewById(R.id.createAccountGender);
        createAccountClear = (Button)findViewById(R.id.createAccountClear);
        createAccountRegister = (Button)findViewById(R.id.createAccountRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createAccountRegister:

                String idNumber,emailAddress,gender,password;

                idNumber = createAccountIdNumber.getText().toString();
                emailAddress=createAccountEmailAddress.getText().toString();
                gender = createAccountGender.getSelectedItem().toString();
                password = createAccountPassword.getText().toString();

                createAccount(idNumber,emailAddress,gender,password);
                break;
            case R.id.createAccountClear:

                break;
        }
    }

    private void createAccount(String idNumber, String emailAddress, String gender, String password) {

        showProgress(true);
        StringRequest request=new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showProgress(false);

                Log.i(TAG, "onResponse: Response= "+response);

                switch (response){
                    case "0":
                        SimpleToast.ok(getBaseContext(),"Registration Success");

                        startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "1":

                        SimpleToast.warning(getBaseContext(),"Registration Failure, try again");
                        break;
                    case "2":

                        SimpleToast.error(getBaseContext(),"Server Error");
                        break;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showProgress(false);

                SimpleToast.error(getBaseContext(),"Server Error, try again");

                Log.e(TAG, "onErrorResponse: Error = "+error.getMessage() );
            }
        }){

        };

        AppController.getInstance().addToRequestQueue(request);
    }

    private void showProgress(final boolean show) {
        if(progressDialog!=null) {
            if (show) {
                progressDialog = new ProgressDialog(CreateAccountActivity.this);
                progressDialog.setMessage("Creating Account...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }
}
