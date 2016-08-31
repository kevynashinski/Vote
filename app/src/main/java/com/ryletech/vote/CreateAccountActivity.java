package com.ryletech.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pierry.simpletoast.SimpleToast;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.Map;

import static com.ryletech.vote.AppConfig.EMAIL_ADDRESS;
import static com.ryletech.vote.AppConfig.ID_NUMBER;
import static com.ryletech.vote.AppConfig.PASSWORD;
import static com.ryletech.vote.AppConfig.REGISTER_URL;
import static com.ryletech.vote.AppConfig.TAG;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    EditText createAccountPassword;
    EditText createAccountEmailAddress;
    EditText createAccountIdNumber;
    Button createAccountClear,createAccountRegister;
    //    MaterialSpinner createAccountGender;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

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
//        createAccountGender = (MaterialSpinner) findViewById(R.id.createAccountGender);
        createAccountPassword = (EditText)findViewById(R.id.createAccountPassword);
//        createAccountGender = (MaterialSpinner)findViewById(R.id.createAccountGender);
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
//                gender = createAccountGender.getSelectedItem().toString();
                password = createAccountPassword.getText().toString();

                createAccount(idNumber, emailAddress, password);
                break;
            case R.id.createAccountClear:

                break;
        }
    }

    private void createAccount(final String idNumber, final String emailAddress, final String password) {

        showProgress(true);
        StringRequest request=new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showProgress(false);

                Log.i(TAG, "onResponse: Response= "+response);

                switch (response){
                    case "0":

                        Prefs.putString(ID_NUMBER, idNumber);
                        Prefs.putString(EMAIL_ADDRESS, emailAddress);

                        SimpleToast.ok(getBaseContext(),"Registration Success");

                        startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "1":

                        SimpleToast.warning(getBaseContext(),"Registration Failure, try again");
                        break;
                    case "2":

                        SimpleToast.warning(getBaseContext(), "Account already exists");
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ID_NUMBER, idNumber);
                params.put(EMAIL_ADDRESS, emailAddress);
//                params.put(GENDER, gender);
                params.put(PASSWORD, password);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    private void showProgress(final boolean show) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                close this activity and return to previous one if any
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
