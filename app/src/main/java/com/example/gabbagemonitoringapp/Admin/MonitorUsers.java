package com.example.gabbagemonitoringapp.Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Adapters.MonitorUsersAdapter;
import com.example.gabbagemonitoringapp.Models.MonitorUsersModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Registrations.first_registration;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MonitorUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MonitorUsersModel> mData;
    MonitorUsersAdapter adapter;
    TextView heading;
    SessionManager sessionManager;
    Urls urls;
    String getID;
    Dialog error_message, no_message;
    MaterialCardView linear_fliter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_users);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        urls = new Urls();

        error_message = new Dialog(this);
        no_message = new Dialog(this);

        heading = findViewById(R.id.heading);
        linear_fliter = findViewById(R.id.linear_fliter);

        heading.setText("Monitor users");

        recyclerView = findViewById(R.id.recyclerview_farmer_products);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MonitorUsersAdapter(this, mData);
        recyclerView.setAdapter(adapter);


        loadUsers();
    }


    private void loadUsers() {
        final ProgressDialog progressDialog = new ProgressDialog(MonitorUsers.this);
        progressDialog.setMessage("Loading users please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_USERS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message = new Dialog(this);
                            no_message.setContentView(R.layout.no_results);
                            MaterialCardView next = no_message.findViewById(R.id.no_results_next);

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent aa = new Intent(MonitorUsers.this, MainAdmin.class);
                                    startActivity(aa);
                                    finish();
                                    no_message.dismiss();
                                }
                            });

                            no_message.setCancelable(false);
                            no_message.setCanceledOnTouchOutside(false);
                            Objects.requireNonNull(no_message.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            no_message.show();

                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);

                                String id = inputsObjects.getString("id");
                                String name = inputsObjects.getString("fullnames");
                                String address = inputsObjects.getString("address");
//                                String numberOfPickUps = inputsObjects.getString("numberOfPickUps");
                                String numberOfPickUps = "0";
                                String date_registered = inputsObjects.getString("date_of_creation");


                                MonitorUsersModel inputsModel = new MonitorUsersModel(id,
                                        name,
                                        address,
                                        numberOfPickUps,date_registered
                                       );
                                mData.add(inputsModel);
                            }
                            adapter = new MonitorUsersAdapter(MonitorUsers.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(MonitorUsers.this, MainAdmin.class);
                                startActivity(aa);
                                finish();
                                error_message.dismiss();
                            }
                        });

                        error_message.setCancelable(false);
                        error_message.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(error_message.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        error_message.show();
                    }
                }, error -> {
            progressDialog.dismiss();
            error_message = new Dialog(this);
            error_message.setContentView(R.layout.error_message);
            MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
            TextView error_txs = error_message.findViewById(R.id.error_tx);

            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(MonitorUsers.this, MainAdmin.class);
                    startActivity(aa);
                    finish();
                    error_message.dismiss();
                }
            });

            error_message.setCancelable(false);
            error_message.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(error_message.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            error_message.show();
        });
        Volley.newRequestQueue(MonitorUsers.this).add(stringRequest);
    }


    public void back(View view) {
        startActivity(new Intent(MonitorUsers.this, MainAdmin.class));
        finish();
    }


    public void addUsers(View view) {
        // this is where the users will be added and the bins attached to them plus the coordinates
        startActivity(new Intent(MonitorUsers.this, first_registration.class));
    }
}