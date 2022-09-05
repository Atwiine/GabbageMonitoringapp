package com.example.gabbagemonitoringapp.Trash;

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
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Adapters.ReviewAdapter;
import com.example.gabbagemonitoringapp.MainActivity;
import com.example.gabbagemonitoringapp.Models.ReviewModel;
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

public class Review extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ReviewModel> mData;
    ReviewAdapter adapter;
    TextView heading;
    SessionManager sessionManager;
    Urls urls;
    String getID;
    Dialog error_message, no_message;
    MaterialCardView linear_fliter,linear_fliter_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        urls = new Urls();

        error_message = new Dialog(this);
        no_message = new Dialog(this);

        heading = findViewById(R.id.heading);
        linear_fliter = findViewById(R.id.linear_fliter);
        linear_fliter_all = findViewById(R.id.linear_fliter_all);

        heading.setText("Review records");

        recyclerView = findViewById(R.id.recyclerview_farmer_products);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        /**/
        String filtered = "";
        loadOthersBooks(filtered);
    }


    private void loadOthersBooks(String filtered) {
        final ProgressDialog progressDialog = new ProgressDialog(Review.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEE_MY_ORDERS,
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
                                    Intent aa = new Intent(Review.this, MainActivity.class);
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


                                ReviewModel inputsModel = new ReviewModel(id,
                                        bin_number,
                                        image,
                                        bin_location,date_ordered,""
                                        ,status, "");
                                mData.add(inputsModel);
                            }
                            adapter = new ReviewAdapter(Review.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
//                        Toast.makeText(this, "ee "+e.toString() , Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Review.this, MainActivity.class);
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
//            Toast.makeText(this, "ee "+error.toString() , Toast.LENGTH_SHORT).show();
            error_message = new Dialog(this);
            error_message.setContentView(R.layout.error_message);
            MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
            TextView error_txs = error_message.findViewById(R.id.error_tx);

            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(Review.this, MainActivity.class);
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
                params.put("status", filtered);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void back(View view) {
        startActivity(new Intent(Review.this, MainActivity.class));
        finish();
    }



    public void showflirtered(View view) {
        heading.setText("Pending records");
clear();
        String filtered = "pending";
        loadOthersBooks(filtered);
        linear_fliter.setVisibility(View.GONE);
        linear_fliter_all.setVisibility(View.VISIBLE);

    }

    public void clear() {
        int size = mData.size();
        if (size > 0) {
            mData.subList(0, size).clear();
            adapter.notifyItemRangeRemoved(0, size);
        }
    }

    public void showall(View view) {
        heading.setText("Review records");
        clear();
        String filtered = "";
        loadOthersBooks(filtered);
        linear_fliter_all.setVisibility(View.GONE);
        linear_fliter.setVisibility(View.VISIBLE);
    }
}