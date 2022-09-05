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
import com.example.gabbagemonitoringapp.Adapters.MonitorPickUpsAdapter;
import com.example.gabbagemonitoringapp.Models.MonitorPickUpsModel;
import com.example.gabbagemonitoringapp.R;
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

public class MonitorPickUpOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MonitorPickUpsModel> mData;
    MonitorPickUpsAdapter adapter;
    TextView heading;
    SessionManager sessionManager;
    Urls urls;
    String getID;
    Dialog error_message, no_message;
    MaterialCardView linear_fliter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_pick_ups);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        urls = new Urls();

        error_message = new Dialog(this);
        no_message = new Dialog(this);

        heading = findViewById(R.id.heading);
        linear_fliter = findViewById(R.id.linear_fliter);

        heading.setText("Monitor pick up orders");

        recyclerView = findViewById(R.id.recyclerview_farmer_products);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MonitorPickUpsAdapter(this, mData);
        recyclerView.setAdapter(adapter);


        loadPickUpOrders();
    }


    private void loadPickUpOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(MonitorPickUpOrders.this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_PICKUP_ORDERS,
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
                                    Intent aa = new Intent(MonitorPickUpOrders.this, MainAdmin.class);
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
                                String bin_number = inputsObjects.getString("bin_number");
                                String image = inputsObjects.getString("image");
                                String bin_location = inputsObjects.getString("bin_location");
                                String date_ordered = inputsObjects.getString("date_ordered");
                                String status = inputsObjects.getString("status");
                                String from = inputsObjects.getString("from");
                                String longitude = inputsObjects.getString("longitude");
                                String latitude = inputsObjects.getString("latitude");

                                MonitorPickUpsModel inputsModel = new MonitorPickUpsModel(id,image,
                                        bin_number,
                                        bin_location,from,date_ordered
                                        ,status,longitude,latitude);
                                mData.add(inputsModel);
                            }
                            adapter = new MonitorPickUpsAdapter(MonitorPickUpOrders.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);
                        error_txs.setText(e.toString());
                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(MonitorPickUpOrders.this, MainAdmin.class);
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
            error_txs.setText(error.toString());
            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(MonitorPickUpOrders.this, MainAdmin.class);
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
        Volley.newRequestQueue(MonitorPickUpOrders.this).add(stringRequest);
    }


    public void back(View view) {
        startActivity(new Intent(MonitorPickUpOrders.this, MainAdmin.class));
        finish();
    }

}