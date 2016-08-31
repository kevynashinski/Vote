package com.ryletech.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pierry.simpletoast.SimpleToast;

import java.util.HashMap;
import java.util.Map;

import static com.ryletech.vote.AppConfig.ID_NUMBER;
import static com.ryletech.vote.AppConfig.LOGIN_URL;
import static com.ryletech.vote.AppConfig.PASSWORD;
import static com.ryletech.vote.AppConfig.TAG;

/**
 * A login screen that offers login via txtIdNumber/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    ProgressDialog progressDialog;

    // UI references.
    private EditText mIdNumber;
    private EditText mPasswordView;
    Button createAccountButton;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

assignViews();

        createAccountButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    private void assignViews() {
        createAccountButton = (Button)findViewById(R.id.createAccountButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        mIdNumber = (EditText) findViewById(R.id.txtIdNumber);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid txtIdNumber, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mIdNumber.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String idNumber = mIdNumber.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid txtIdNumber address.
        if (TextUtils.isEmpty(idNumber)) {
            mIdNumber.setError(getString(R.string.error_field_required));
            focusView = mIdNumber;
            cancel = true;
        }
        if(idNumber.length() < 6){
            mIdNumber.setError("Enter a Valid Id Number");
            focusView = mIdNumber;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            authenticate(idNumber, password);
        }
    }

    private void authenticate(final String idNumber, final String password) {
        showProgress(true);

        StringRequest request=new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: Response= "+response);
                showProgress(false);

                switch (response){
                    case "0":
                        SimpleToast.ok(getBaseContext(),"Login Success");

                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "1":

                        SimpleToast.warning(getBaseContext(),"Invalid Id Number or Password");
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
                Log.e(TAG, "onErrorResponse: Error = "+error.getMessage() );
                SimpleToast.error(getBaseContext(),"Server Error, try again");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ID_NUMBER, idNumber);
                params.put(PASSWORD, password);
                return params;            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        if(progressDialog!=null) {
            if (show) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Authenticating...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButton:

                attemptLogin();
                break;
            case R.id.createAccountButton:

                startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
                break;
        }
    }
}

