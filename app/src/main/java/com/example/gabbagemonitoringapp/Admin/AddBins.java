package com.example.gabbagemonitoringapp.Admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AddBins extends AppCompatActivity {

    MaterialCardView add_product, upload_image;
    ImageView image_preview;
    EditText bin_number, bin_location, last_inspection
            ,conditon_after_inspection,bin_location_long,bin_location_lat,bin_incharge;
    Urls urls;
    SessionManager sessionManager;
    String getID, getName;
    Dialog ss_card, ee_card;
    CalendarView calendar;


    //getting the bin coordinates
    private FusedLocationProviderClient mFusedLocationClient;

    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private MaterialCardView btnLocation,btnNext;
    private TextView txtLocation;
    private Button btnContinueLocation;
    private TextView txtContinueLocation;
    private StringBuilder stringBuilder;

    private boolean isContinue = false;
    private boolean isGPS = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bin);

        ss_card = new Dialog(this);
        ee_card = new Dialog(this);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getName = user.get(SessionManager.FULLNAME);
        urls = new Urls();

        bin_location = findViewById(R.id.bin_location);
        add_product = findViewById(R.id.add_product);
        bin_number = findViewById(R.id.bin_number);
        conditon_after_inspection = findViewById(R.id.conditon_after_inspection);
        last_inspection = findViewById(R.id.last_inspection);
        calendar = findViewById(R.id.calender);

        bin_location_long = findViewById(R.id.bin_location_long);
        bin_location_lat = findViewById(R.id.bin_location_lat);
        bin_incharge = findViewById(R.id.bin_incharge);

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




        ///the location part
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        if (!isContinue) {
                            bin_location_long.setText(String.format(Locale.US, "%s",  wayLongitude));
                            bin_location_lat.setText(String.format(Locale.US, "%s", wayLatitude));

                        } else {
                            stringBuilder.append(wayLatitude);
                            stringBuilder.append("-");
                            stringBuilder.append(wayLongitude);
                            stringBuilder.append("\n\n");
                            txtContinueLocation.setText(stringBuilder.toString());
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };


        //gets the coordinates
        if (!isGPS) {
            Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        isContinue = false;
        getLocation();


    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(AddBins.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(AddBins.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddBins.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(AddBins.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
//                        txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                        bin_location_long.setText(String.format(Locale.US, "%s",  wayLongitude));
                        bin_location_lat.setText(String.format(Locale.US, "%s", wayLatitude));

//                        btnNext.setVisibility(View.VISIBLE);
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isContinue) {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                } else {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(AddBins.this, location -> {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                        } else {
                            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    });
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }


    public void submitOrder(View view) {

        /*submitting part*/

        String bt = bin_number.getText().toString().trim();
        String bs = bin_location.getText().toString().trim();
        String btla = bin_location_lat.getText().toString().trim();
        String bslo = bin_location_long.getText().toString().trim();
        String binch = bin_incharge.getText().toString().trim();

        if (!bt.isEmpty() && !bs.isEmpty() && !btla.isEmpty() && !bslo.isEmpty() && !binch.isEmpty() ) {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    AddBins.this);
            alertDialog2.setTitle("Confirm before submitting");
            alertDialog2.setMessage("Make sure you double check before registering the bin");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) ->
                            sendBin());
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        } else {
            Toast.makeText(AddBins.this, "All fields are required", Toast.LENGTH_SHORT).show();

        }

    }


    private void sendBin() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        String bt = bin_number.getText().toString().trim();
        String bs = bin_location.getText().toString().trim();
        String ll = last_inspection.getText().toString().trim();
        String cc = conditon_after_inspection.getText().toString().trim();
        String btla = bin_location_lat.getText().toString().trim();
        String bslo = bin_location_long.getText().toString().trim();
        String binch = bin_incharge.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPLOAD_BIN,

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

                            success_type.setText("Bin was registered successful");
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent aa = new Intent(AddBins.this, ManageBins.class);
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
                        TextView success_type = ee_card.findViewById(R.id.tx_success);

                        success_type.setText("failed to register bin, please try again");
                        dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Intent as = new Intent(BookDetails.this,MainActivity.class);
//                                    startActivity(as);
//                                    finish();
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
                    TextView success_type = ee_card.findViewById(R.id.tx_success);

                    success_type.setText("failed to register bin, please check your connection and try again");
                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                    Intent as = new Intent(BookDetails.this,MainActivity.class);
//                                    startActivity(as);
//                                    finish();
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
                params.put("userid", getID);
                params.put("binNo", bt);
                params.put("binLocation", bs);
                params.put("last_inspection", ll);
                params.put("conditon_after_inspection", cc);
                params.put("incharge", binch);
                params.put("longitude", bslo);
                params.put("latitude", btla);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void back(View view) {
        startActivity(new Intent(AddBins.this, ManageBins.class));
        finish();
    }

}