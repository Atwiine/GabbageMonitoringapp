package com.example.gabbagemonitoringapp.Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BinDetails extends AppCompatActivity {

    MaterialCardView add_product;
    EditText bin_number, bin_location, last_inspection,conditon_after_inspection,status,date,bin_long,bin_lat;
    TextView heading,id;
    SessionManager sessionManager;
    Urls urls;
    String getID;
    LinearLayout linear_fliter;
    Dialog ss_card, ee_card;
    CalendarView calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_details);

        ss_card = new Dialog(this);
        ee_card = new Dialog(this);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        urls = new Urls();


        heading = findViewById(R.id.heading);
        linear_fliter = findViewById(R.id.linear_fliter);
        bin_location = findViewById(R.id.bin_location);
        add_product = findViewById(R.id.add_product);
        bin_number = findViewById(R.id.bin_number);
        conditon_after_inspection = findViewById(R.id.conditon_after_inspection);
        last_inspection = findViewById(R.id.last_inspection);
        calendar = findViewById(R.id.calender);
        id = findViewById(R.id.id);
        status = findViewById(R.id.status);
        date = findViewById(R.id.date);
        bin_long = findViewById(R.id.bin_long);
        bin_lat = findViewById(R.id.bin_lat);

        heading.setText("Bin details");

        bin_location.setText(getIntent().getStringExtra("bLocation"));
        bin_number.setText(getIntent().getStringExtra("bNumber"));
        conditon_after_inspection.setText(getIntent().getStringExtra("condition_after_inspection"));
        id.setText(getIntent().getStringExtra("id"));
        last_inspection.setText(getIntent().getStringExtra("last_inspection"));
        status.setText(getIntent().getStringExtra("status"));
        date.setText(getIntent().getStringExtra("date"));
        bin_long.setText(getIntent().getStringExtra("longitude"));
        bin_lat.setText(getIntent().getStringExtra("latitude"));

        last_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setVisibility(View.VISIBLE);
            }
        });


// Add Listener in calendar
        calendar
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                String Date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;

                                // set this date in TextView for Display
                                last_inspection.setText(Date);
                                calendar.setVisibility(View.GONE);
                            }
                        });
    }


    public void submitOrder(View view) {

        /*submitting part*/

        String bt = bin_number.getText().toString().trim();
        String bs = bin_location.getText().toString().trim();

        if (!bt.isEmpty() && !bs.isEmpty() ) {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    BinDetails.this);
            alertDialog2.setTitle("Confirm before submitting");
            alertDialog2.setMessage("Make sure you double check before updating the bin registration details");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) ->
                            sendUpdateBin());
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        } else {
            Toast.makeText(BinDetails.this, "All fields are required", Toast.LENGTH_SHORT).show();

        }

    }

    private void sendUpdateBin() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        String bt = bin_number.getText().toString().trim();
        String bs = bin_location.getText().toString().trim();
        String ll = last_inspection.getText().toString().trim();
        String cc = conditon_after_inspection.getText().toString().trim();
        String ssss = status.getText().toString().trim();
        String idd = id.getText().toString().trim();
        String btla = bin_lat.getText().toString().trim();
        String bslo = bin_long.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.EDIT_BIN,

                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();

                            ss_card = new Dialog(this);
                            ss_card.setContentView(R.layout.right);
                            CardView dismiss = ss_card.findViewById(R.id.ss_card);
                            TextView success_type = ss_card.findViewById(R.id.tx_success);

                            success_type.setText("Bin was updated successful");
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent aa = new Intent(BinDetails.this, ManageBins.class);
                                    startActivity(aa);
                                    finish();
                                    ss_card.dismiss();
                                }
                            });

                            ss_card.setCancelable(false);
                            ss_card.setCanceledOnTouchOutside(false);
                            Objects.requireNonNull(ss_card.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            ss_card.show();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        ee_card = new Dialog(this);
                        ee_card.setContentView(R.layout.error);
                        CardView dismiss = ee_card.findViewById(R.id.ee_card);
                        TextView success_type = ee_card.findViewById(R.id.tx_error);

                        success_type.setText("failed to update bin, please try again");
                        dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    Intent as = new Intent(BinDetails.this, ManageBins.class);
                                    startActivity(as);
                                    finish();
                                ee_card.dismiss();
                            }
                        });

                        ee_card.setCancelable(false);
                        ee_card.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(ee_card.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        ee_card.show();

                    }
                },

                error -> {
                    dialog.dismiss();
//                    Toast.makeText(SendPickUp.this, "failed to  upload your book'cover and summary, please check your connection and try again" +
//                            error.toString(), Toast.LENGTH_SHORT).show();

                    ee_card = new Dialog(this);
                    ee_card.setContentView(R.layout.error);
                    CardView dismiss = ee_card.findViewById(R.id.ee_card);
                    TextView success_type = ee_card.findViewById(R.id.tx_error);

                    success_type.setText("failed to update bin, please check your connection and try again" +error.toString());
                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent as = new Intent(BinDetails.this, ManageBins.class);
                                    startActivity(as);
                                    finish();
                            ee_card.dismiss();
                        }
                    });

                    ee_card.setCancelable(false);
                    ee_card.setCanceledOnTouchOutside(false);
                    Objects.requireNonNull(ee_card.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ee_card.show();
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("binNo", bt);
                params.put("id", idd);
                params.put("binLocation", bs);
                params.put("last_inspection", ll);
                params.put("condition_after_inspection", cc);
                params.put("status", ssss);
                params.put("longitude", bslo);
                params.put("latitude", btla);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void back(View view) {
        startActivity(new Intent(BinDetails.this, ManageBins.class));
        finish();
    }

}