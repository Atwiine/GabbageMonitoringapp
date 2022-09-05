package com.example.gabbagemonitoringapp.Admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Preferences.SettingsActivity;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Registrations.LoginSecond;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class MainAdmin extends AppCompatActivity {

    TextView user_name,address;
    Dialog settings_popup, error_message, no_message,no_internet;
    ImageView reader_dp;
    SessionManager sessionManager;
    Urls urls;
    String getTYPE,getName,getID;
    boolean connected = false;
    TextView total_bins,total_pickups,total_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        settings_popup = new Dialog(this);

        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();

//add the control if statement for the session manager

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getName = user.get(SessionManager.USERNAME);
        getTYPE = user.get(SessionManager.TYPE);
        urls = new Urls();

        if (!getTYPE.equals("Admin")){
            startActivity(new Intent(MainAdmin.this, LoginSecond.class));
            finish();
        }

        user_name =findViewById(R.id.user_name);
        user_name.setText(getName);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ){
            connected = true;

        }else {
            connected = false;
            no_internet = new Dialog(this);
            no_internet.setContentView(R.layout.no_internet);
            MaterialCardView no_conn = no_internet.findViewById(R.id.no_conn);

            no_conn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    System.exit(0);
                    no_internet.dismiss();
                }
            });

            no_internet.setCancelable(false);
            no_internet.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(no_internet.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            no_internet.show();
        }
        total_bins = findViewById(R.id.total_bins);
        total_pickups = findViewById(R.id.total_pickups);
        total_users = findViewById(R.id.total_users);

        numberBins();
        numberUser();
        numberPickups();
    }

    private void numberBins() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.COUNTBINS,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            total_bins.setText("0");
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);
                                String total = inputsObjects.getString("total");
                                total_bins.setText(total);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        total_bins.setText("0");
                    }
                }, error -> {
            total_bins.setText("0");
        }) ;
        Volley.newRequestQueue(MainAdmin.this).add(stringRequest);
    }

    private void numberUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.COUNTUSERS,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            total_users.setText("0");
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);
                                String total = inputsObjects.getString("total");
                                total_users.setText(total);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        total_bins.setText("0");
                    }
                }, error -> {
            total_bins.setText("0");
        }) ;
        Volley.newRequestQueue(MainAdmin.this).add(stringRequest);
    }


    private void numberPickups() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.COUNTPICKUPS,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            total_pickups.setText("0");
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);
                                String total = inputsObjects.getString("total");
                                total_pickups.setText(total);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        total_bins.setText("0");
                    }
                }, error -> {
            total_bins.setText("0");
        });
        Volley.newRequestQueue(MainAdmin.this).add(stringRequest);
    }


    public void openMenu(View view) {
        SettingsActivity settingsActivity = new SettingsActivity();
        settingsActivity.show(getSupportFragmentManager(),
                "ModalBottomSheet");

    }

    public void openmanagebins(View view) {
        startActivity(new Intent(MainAdmin.this,ManageBins.class));//done
    }

    public void openmonitorRecords(View view) {
        startActivity(new Intent(MainAdmin.this,MonitorPickUpOrders.class));//done
    }

    public void openmonitorusers(View view) {
        startActivity(new Intent(MainAdmin.this,MonitorUsers.class));//done
    }

    public void openRewards(View view) {
        startActivity(new Intent(MainAdmin.this,ManageRewards.class));//done
    }
}