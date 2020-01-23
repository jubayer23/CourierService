package com.example.courierservice;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.courierservice.Utility.CommonMethods;
import com.example.courierservice.Utility.DeviceInfoUtils;
import com.example.courierservice.Utility.RunnTimePermissions;
import com.example.courierservice.alertbanner.AlertDialogForAnything;
import com.example.courierservice.appdata.DummyResponse;
import com.example.courierservice.appdata.GlobalAppAccess;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.model.User;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText ed_username, ed_password;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        RunnTimePermissions.requestForAllRuntimePermissions(this);
    }

    private void init() {
        ed_username = findViewById(R.id.ed_username);
        ed_username.setText(MydApplication.getInstance().getPrefManger().getEmailCache());
        ed_password = findViewById(R.id.ed_password);

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = R.id.btn_submit;




        if (id == R.id.btn_submit) {

            if (!DeviceInfoUtils.isConnectingToInternet(LoginActivity.this)) {
                AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "ERROR", "Please connect to a working internet connection!", false);
                return;
            }

            if (!RunnTimePermissions.requestForAllRuntimePermissions(this)) {
                return;
            }

            if (isValidCredentialsProvided()) {

                CommonMethods.hideKeyboardForcely(this, ed_username);
                CommonMethods.hideKeyboardForcely(this, ed_password);

                String deviceId = DeviceInfoUtils.getDeviceImieNumber(this);

                //saveCache(ed_email.getText().toString());

                sendRequestForLogin(GlobalAppAccess.URL_LOGIN, ed_username.getText().toString(), ed_password.getText().toString(),deviceId);
                //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean isValidCredentialsProvided() {

        // Store values at the time of the login attempt.
        String username = ed_username.getText().toString();
        String password = ed_password.getText().toString();

        // Reset errors.
        ed_username.setError(null);
        ed_password.setError(null);
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            ed_username.setError("Required");
            ed_username.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ed_password.setError("Required");
            ed_password.requestFocus();
            return false;
        }

        return true;
    }


    public void sendRequestForLogin(String url, final String userName, final String password, final String deviceId) {

        //url = url + "?" + "userName=" + userName + "&password=" + password;
        // TODO Auto-generated method stub
        showProgressDialog("Loading..", true, false);

        Log.d("DEBUG", url);

        final StringRequest req = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("debug",response);


                        dismissProgressDialog();
                        try{
                            User user = MydApplication.gson.fromJson(response, User.class);
                            if(user.getStatus()){
                                MydApplication.getInstance().getPrefManger().setEmailCache(userName);
                                MydApplication.getInstance().getPrefManger().setUser(user);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, user.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(LoginActivity.this, "Something went wrong while parsing!", Toast.LENGTH_LONG).show();
                        }



                        //Log.d("DEBUG",response);



                        Log.d("Debug", response);


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dismissProgressDialog();

                Log.d("debug", error.getMessage().toString());

                AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "Error", "Network problem. please try again!", false);

                User user = MydApplication.gson.fromJson(DummyResponse.getUserInfo(), User.class);
                if(user.getStatus()){
                    MydApplication.getInstance().getPrefManger().setEmailCache(userName);
                    MydApplication.getInstance().getPrefManger().setUser(user);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, user.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usernameOrEmail", userName);
                params.put("password", password);
                params.put("sourceId", "100");
                params.put("deviceId", deviceId);
                return params;
            }
        };


        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(req);
    }

}

