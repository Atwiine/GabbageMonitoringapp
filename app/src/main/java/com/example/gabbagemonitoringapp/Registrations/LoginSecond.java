package com.example.gabbagemonitoringapp.Registrations;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginSecond extends AppCompatActivity {
    Urls urls;
    TextView heading, register;
    EditText names, etPassword,name_loginadmin,etPasswordadmin;
    CardView login,buttonadmin,button;
    SessionManager sessionManager;
    Dialog not_confirmed, denied_access, suspended_access;
    String action;
    LinearLayout linear_user,linear_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);
        sessionManager = new SessionManager(this);
        urls = new Urls();

        not_confirmed = new Dialog(this);
        denied_access = new Dialog(this);
        suspended_access = new Dialog(this);


        heading = findViewById(R.id.textView);
        names = findViewById(R.id.name_login);
        etPassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.button);
        register = findViewById(R.id.reg);

        name_loginadmin = findViewById(R.id.name_loginadmin);
        etPasswordadmin = findViewById(R.id.etPasswordadmin);
        linear_user = findViewById(R.id.linear_user);
        linear_admin = findViewById(R.id.linear_admin);
        buttonadmin = findViewById(R.id.buttonadmin);


//
//        register.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginSecond.this, first_registration.class);
//            startActivity(intent);
//        });

        login.setOnClickListener(v -> {
            String user = names.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (!user.isEmpty() || !pass.isEmpty()) {
                Login(user);

            } else {
                names.setError("Enter your bin number");
                etPassword.setError("Enter  Password");
            }
        });



        buttonadmin.setOnClickListener(v -> {
            String user = name_loginadmin.getText().toString().trim();
            String pass = etPasswordadmin.getText().toString().trim();

            if (!user.isEmpty() || !pass.isEmpty()) {
                LoginAdmin(user,pass);

            } else {
                name_loginadmin.setError("Enter username");
                etPasswordadmin.setError("Enter  Password");
            }
        });
    }

    //ADD THE VOLLEY THING

    private void Login(final String name) {

        final ProgressDialog loading = new ProgressDialog(LoginSecond.this);
        loading.setMessage("logging in please wait...");
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOGIN_URL,
                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")) {
                            Toast.makeText(LoginSecond.this, "login success", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("userid");
                                String username = object.getString("username");
                                String fullname = object.getString("fullname");
//                                String password1 = object.getString("password");
                                String type = object.getString("type");//who is using the app

                                String bin_number = object.getString("bin_number");
                                String latitude = object.getString("latitude");
                                String longitude = object.getString("longitude");


                                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(username,  fullname, id, "password1",type,bin_number,
                                        latitude,longitude);
                                        Intent intent = new Intent(LoginSecond.this, CheckFirst.class);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                        finish();
                                }
                                loading.dismiss();

                        } else if (success.equals("0")) {
                            loading.dismiss();
//                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginSecond.this, "login was error account not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.dismiss();
//                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginSecond.this, "failed to login, please try again ", Toast.LENGTH_LONG).show();
                    }
                },

                error -> {
                    loading.dismiss();
//                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginSecond.this, "login error please check your internet connection and try again ", Toast.LENGTH_SHORT).show();
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bin_number", name);//user the user name not the full name
//                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void LoginAdmin(final String name,final String password) {

        final ProgressDialog loading = new ProgressDialog(LoginSecond.this);
        loading.setMessage("logging in please wait...");
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.ADMINLOGIN_URL,
                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")) {
                            Toast.makeText(LoginSecond.this, "login success", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("userid");
                                String username = object.getString("username");
                                String fullname = object.getString("fullname");
                                String password1 = object.getString("password");
                                String type = object.getString("type");//who is using the app

                                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(username,  fullname, id, password1,type,"bin_number",
                                        "latitude","longitude");
                                Intent intent = new Intent(LoginSecond.this, CheckFirst.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();
                            }
                            loading.dismiss();

                        } else if (success.equals("0")) {
                            loading.dismiss();
//                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginSecond.this, "login was error account not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.dismiss();
//                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginSecond.this, "failed to login, please try again ", Toast.LENGTH_LONG).show();
                    }
                },

                error -> {
                    loading.dismiss();
//                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginSecond.this, "login error please check your internet connection and try again ", Toast.LENGTH_SHORT).show();
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", name);//user the user name not the full name
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void loginAdmin(View view) {
        linear_user.setVisibility(View.GONE);
        linear_admin.setVisibility(View.VISIBLE);
    }

    public void loginUser(View view) {
        linear_admin.setVisibility(View.GONE);
        linear_user.setVisibility(View.VISIBLE);
    }
}