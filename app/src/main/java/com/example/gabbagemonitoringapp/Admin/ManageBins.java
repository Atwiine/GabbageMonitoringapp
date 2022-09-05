package com.example.gabbagemonitoringapp.Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Adapters.ManageBinsAdapter;
import com.example.gabbagemonitoringapp.Models.ManageBinsModel;
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
import java.util.Map;
import java.util.Objects;

public class ManageBins extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ManageBinsModel> mData;
    ManageBinsAdapter adapter;
    TextView heading;
    SessionManager sessionManager;
    Urls urls;
    String getID;
    Dialog error_message, no_message;
    LinearLayout linear_fliter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bins);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        urls = new Urls();

        error_message = new Dialog(this);
        no_message = new Dialog(this);

        heading = findViewById(R.id.heading);
        linear_fliter = findViewById(R.id.linear_fliter);

        heading.setText("Manage bins");

        recyclerView = findViewById(R.id.recyclerview_farmer_products);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ManageBinsAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        loadBins();
    }


    private void loadBins() {
        final ProgressDialog progressDialog = new ProgressDialog(ManageBins.this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_BINS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message = new Dialog(this);
                            no_message.setContentView(R.layout.no_results);
                            MaterialCardView next = no_message.findViewById(R.id.no_results_next);
                            TextView red = no_message.findViewById(R.id.redirect);
                            red.setText("No results found,  you will directed to a page of registering bins");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent aa = new Intent(ManageBins.this, AddBins.class);
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
                                String bin_location = inputsObjects.getString("bin_location");
                                String date = inputsObjects.getString("date");
                                String last_inspection = inputsObjects.getString("last_inspection");
                                String condition_after_inspection = inputsObjects.getString("condition_after_inspection");
                                String status = inputsObjects.getString("status");
                                String longitude = inputsObjects.getString("longitude");
                                String latitude = inputsObjects.getString("latitude");

                                ManageBinsModel inputsModel = new ManageBinsModel(id,
                                        bin_number,
                                        bin_location,date,last_inspection,
                                        condition_after_inspection,status,latitude,longitude);
                                mData.add(inputsModel);
                            }
                            adapter = new ManageBinsAdapter(ManageBins.this, mData);
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
                                Intent aa = new Intent(ManageBins.this, MainAdmin.class);
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
            Toast.makeText(this, "error " + error.toString(), Toast.LENGTH_SHORT).show();
            error_message = new Dialog(this);
            error_message.setContentView(R.layout.error_message);
            MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
            TextView error_txs = error_message.findViewById(R.id.error_tx);

            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(ManageBins.this, MainAdmin.class);
                    startActivity(aa);
                    finish();
                    error_message.dismiss();
                }
            });

            error_message.setCancelable(false);
            error_message.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(error_message.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            error_message.show();
        }){
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void back(View view) {
        startActivity(new Intent(ManageBins.this, MainAdmin.class));
        finish();
    }

    public void openFliter(View view) {
        linear_fliter.setVisibility(View.VISIBLE);
    }

    public void addBins(View view) {
        startActivity(new Intent(ManageBins.this, AddBins.class));
    }

    public void clear() {
        int size = mData.size();
        if (size > 0) {
            mData.subList(0, size).clear();
            adapter.notifyItemRangeRemoved(0, size);
        }
    }

    public void openReport(View view) {
        startActivity(new Intent(ManageBins.this, Reports.class));
    }
}