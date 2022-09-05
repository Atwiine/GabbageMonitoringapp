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
import com.example.gabbagemonitoringapp.Adapters.ReportAreaAdapter;
import com.example.gabbagemonitoringapp.Adapters.ReportDaysAdapter;
import com.example.gabbagemonitoringapp.Models.ReportAreaModel;
import com.example.gabbagemonitoringapp.Models.ReportDaysModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Reports extends AppCompatActivity {

    RecyclerView recyclerView,recyclerview_farmer_products_weekly,weekly_days_most_request,weekly_clients_most_request
            ,recyclerview_farmer_products_monthly,monthly_days_most_request,monthly_clients_most_request;
    List<ReportAreaModel> mData;
    List<ReportDaysModel> mData2;
    ReportAreaAdapter adapter;
    ReportDaysAdapter adapterdays;
    TextView heading,today_date,total_request_today,week_from_date,week_to_date,total_request_weekly,total_request_monthly
            ,no_message_balance_monthly_most_clients;
    SessionManager sessionManager;
    Urls urls;
    String getID;
    Dialog error_message, no_message;
    LinearLayout linear_fliter,month_reports,weekly_reports,today_reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        urls = new Urls();

        error_message = new Dialog(this);
        no_message = new Dialog(this);

        heading = findViewById(R.id.heading);
        linear_fliter = findViewById(R.id.linear_fliter);

        weekly_reports = findViewById(R.id.weekly_reports);
        week_from_date = findViewById(R.id.week_from_date);
        week_to_date = findViewById(R.id.week_to_date);
        total_request_weekly = findViewById(R.id.total_request_weekly);
        recyclerview_farmer_products_weekly = findViewById(R.id.recyclerview_farmer_products_weekly);
        weekly_clients_most_request = findViewById(R.id.weekly_clients_most_request);

        month_reports = findViewById(R.id.month_reports);
        total_request_monthly = findViewById(R.id.total_request_monthly);
        recyclerview_farmer_products_monthly = findViewById(R.id.recyclerview_farmer_products_monthly);
        monthly_clients_most_request = findViewById(R.id.monthly_clients_most_request);
//

        today_reports = findViewById(R.id.today_reports);
        today_date = findViewById(R.id.today_date);
        total_request_today = findViewById(R.id.total_request_today);

        heading.setText("Reports");

        recyclerView = findViewById(R.id.recyclerview_farmer_products);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReportAreaAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        recyclerview_farmer_products_weekly = findViewById(R.id.recyclerview_farmer_products_weekly);
        mData = new ArrayList<>();
        recyclerview_farmer_products_weekly.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReportAreaAdapter(this, mData);
        recyclerview_farmer_products_weekly.setAdapter(adapter);

        recyclerview_farmer_products_monthly = findViewById(R.id.recyclerview_farmer_products_monthly);
        mData = new ArrayList<>();
        recyclerview_farmer_products_monthly.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReportAreaAdapter(this, mData);
        recyclerview_farmer_products_monthly.setAdapter(adapter);


/*for the days with the most orders*/
        weekly_days_most_request = findViewById(R.id.weekly_days_most_request);
        mData2 = new ArrayList<>();
        weekly_days_most_request.setLayoutManager(new LinearLayoutManager(this));
        adapterdays = new ReportDaysAdapter(this, mData2);
        weekly_days_most_request.setAdapter(adapterdays);

        monthly_days_most_request = findViewById(R.id.monthly_days_most_request);
        mData2 = new ArrayList<>();
        monthly_days_most_request.setLayoutManager(new LinearLayoutManager(this));
        adapterdays = new ReportDaysAdapter(this, mData2);
        monthly_days_most_request.setAdapter(adapterdays);




        /*set the date*/
        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(todaysdate);
        today_date.setText(date);


        /*load today's report*/
        loadTodayReport();
        loadWeeklyReport();
        loadMonthlyReport();
        loadDaysWeeklyReport();
        loadDaysMonthlyReport();
//        totalPickups();
    }


    private void loadTodayReport() {
        final ProgressDialog progressDialog = new ProgressDialog(Reports.this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_TODAY_REPORT,
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
                            red.setText("No results found");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent aa = new Intent(Reports.this, AddBins.class);
//                                    startActivity(aa);
//                                    finish();
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
                                String area_name = inputsObjects.getString("bin_location");
                                String no_pickups = inputsObjects.getString("number_of_pickups");


                                ReportAreaModel inputsModel = new ReportAreaModel(id,
                                        area_name,
                                        no_pickups,"date");
                                mData.add(inputsModel);
                            }
                            adapter = new ReportAreaAdapter(Reports.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "ee "+e.toString(), Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Reports.this, MainAdmin.class);
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
                    Intent aa = new Intent(Reports.this, MainAdmin.class);
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

    private void loadWeeklyReport() {
        final ProgressDialog progressDialog = new ProgressDialog(Reports.this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_WEEKLY_REPORT,
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
                            red.setText("No results found");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent aa = new Intent(Reports.this, AddBins.class);
//                                    startActivity(aa);
//                                    finish();
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
                                String area_name = inputsObjects.getString("bin_location");
                                String no_pickups = inputsObjects.getString("number_of_pickups");


                                ReportAreaModel inputsModel = new ReportAreaModel(id,
                                        area_name,
                                        no_pickups,"date");
                                mData.add(inputsModel);
                            }
                            adapter = new ReportAreaAdapter(Reports.this, mData);
                            recyclerview_farmer_products_weekly.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "ee "+e.toString(), Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Reports.this, MainAdmin.class);
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
                    Intent aa = new Intent(Reports.this, MainAdmin.class);
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

    private void loadMonthlyReport() {
        final ProgressDialog progressDialog = new ProgressDialog(Reports.this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_MONTHLY_REPORT,
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
                            red.setText("No results found");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent aa = new Intent(Reports.this, AddBins.class);
//                                    startActivity(aa);
//                                    finish();
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
                                String area_name = inputsObjects.getString("bin_location");
                                String no_pickups = inputsObjects.getString("number_of_pickups");

                                ReportAreaModel inputsModel = new ReportAreaModel(id,
                                        area_name,
                                        no_pickups,"date");
                                mData.add(inputsModel);
                            }
                            adapter = new ReportAreaAdapter(Reports.this, mData);
                            recyclerview_farmer_products_monthly.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "ee "+e.toString(), Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Reports.this, MainAdmin.class);
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
                    Intent aa = new Intent(Reports.this, MainAdmin.class);
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


    /*for the most orders in days*/
    private void loadDaysWeeklyReport() {
//        final ProgressDialog progressDialog = new ProgressDialog(Reports.this);
//        progressDialog.setMessage("Loading please wait....");
//        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_DAYWEEKLY_REPORT,
                response -> {
//                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message = new Dialog(this);
                            no_message.setContentView(R.layout.no_results);
                            MaterialCardView next = no_message.findViewById(R.id.no_results_next);
                            TextView red = no_message.findViewById(R.id.redirect);
                            red.setText("No results found");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent aa = new Intent(Reports.this, AddBins.class);
//                                    startActivity(aa);
//                                    finish();
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
                                String date_ordered = inputsObjects.getString("date_ordered");
                                String no_pickups = inputsObjects.getString("number_of_pickups");

                                ReportDaysModel inputsModel = new ReportDaysModel(id,
                                        no_pickups,date_ordered);
                                mData2.add(inputsModel);
                            }
                            adapterdays = new ReportDaysAdapter(Reports.this, mData2);
                            weekly_days_most_request.setAdapter(adapterdays);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "ee "+e.toString(), Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Reports.this, MainAdmin.class);
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
            Toast.makeText(this, "error " + error.toString(), Toast.LENGTH_SHORT).show();
            error_message = new Dialog(this);
            error_message.setContentView(R.layout.error_message);
            MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
            TextView error_txs = error_message.findViewById(R.id.error_tx);

            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(Reports.this, MainAdmin.class);
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

    private void loadDaysMonthlyReport() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_DAYSMONTHLY_REPORT,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message = new Dialog(this);
                            no_message.setContentView(R.layout.no_results);
                            MaterialCardView next = no_message.findViewById(R.id.no_results_next);
                            TextView red = no_message.findViewById(R.id.redirect);
                            red.setText("No results found");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent aa = new Intent(Reports.this, AddBins.class);
//                                    startActivity(aa);
//                                    finish();
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
                                String date_ordered = inputsObjects.getString("date_ordered");
                                String no_pickups = inputsObjects.getString("number_of_pickups");

                                ReportDaysModel inputsModel = new ReportDaysModel(id,

                                        no_pickups,date_ordered);
                                mData2.add(inputsModel);
                            }
                            adapterdays = new ReportDaysAdapter(Reports.this, mData2);
                            monthly_days_most_request.setAdapter(adapterdays);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "ee "+e.toString(), Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Reports.this, MainAdmin.class);
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
            Toast.makeText(this, "error " + error.toString(), Toast.LENGTH_SHORT).show();
            error_message = new Dialog(this);
            error_message.setContentView(R.layout.error_message);
            MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
            TextView error_txs = error_message.findViewById(R.id.error_tx);

            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(Reports.this, MainAdmin.class);
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

    /*number of pickups*/
/*
    private void totalPickups() {
//        final ProgressDialog progressDialog = new ProgressDialog(Reports.this);
//        progressDialog.setMessage("Loading please wait....");
//        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.DAYTODAY_REPORT,
                response -> {
//                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message = new Dialog(this);
                            no_message.setContentView(R.layout.no_results);
                            MaterialCardView next = no_message.findViewById(R.id.no_results_next);
                            TextView red = no_message.findViewById(R.id.redirect);
                            red.setText("No results found");

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent aa = new Intent(Reports.this, AddBins.class);
//                                    startActivity(aa);
//                                    finish();
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

                                String no_pickups = inputsObjects.getString("number_of_pickups");
                                total_request_today.setText(no_pickups);

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "ee "+e.toString(), Toast.LENGTH_SHORT).show();
                        error_message = new Dialog(this);
                        error_message.setContentView(R.layout.error_message);
                        MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
                        TextView error_txs = error_message.findViewById(R.id.error_tx);

                        tryagains.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent aa = new Intent(Reports.this, MainAdmin.class);
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
            Toast.makeText(this, "error " + error.toString(), Toast.LENGTH_SHORT).show();
            error_message = new Dialog(this);
            error_message.setContentView(R.layout.error_message);
            MaterialCardView tryagains = error_message.findViewById(R.id.tryagain);
            TextView error_txs = error_message.findViewById(R.id.error_tx);

            tryagains.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aa = new Intent(Reports.this, MainAdmin.class);
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

*/

//    total_request_today
    public void back(View view) {
        startActivity(new Intent(Reports.this, ManageBins.class));
        finish();
    }


/*    public void clear() {
        int size = mData.size();
        if (size > 0) {
            mData.subList(0, size).clear();
            adapter.notifyItemRangeRemoved(0, size);
        }
    }*/


    public void todayReport(View view) {
        weekly_reports.setVisibility(View.GONE);
        month_reports.setVisibility(View.GONE);
        today_reports.setVisibility(View.VISIBLE);
    }
    public void weekReport(View view) {
        today_reports.setVisibility(View.GONE);
        month_reports.setVisibility(View.GONE);
        weekly_reports.setVisibility(View.VISIBLE);
    }
    public void monthReport(View view) {
        today_reports.setVisibility(View.GONE);
        weekly_reports.setVisibility(View.GONE);
        month_reports.setVisibility(View.VISIBLE);
    }
}