package com.example.gabbagemonitoringapp.Trash;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.MainActivity;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Rewards extends AppCompatActivity {

    TextView heading, c1, c2, c3, c4, c5, c6, r1, r2, r3, r4, r5, r6, rr1, rr2, rr3, rr4, rr5, rr6, total_pickups;
    SessionManager sessionManager;
    Urls urls;
    String getID,getNames;
    LinearLayout gift1, gift2, gift3, gift4, gift5, gift6;
    Dialog ss_card, ee_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        ss_card = new Dialog(this);
        ee_card = new Dialog(this);

        sessionManager = new SessionManager(this);
        heading = findViewById(R.id.heading);
        gift1 = findViewById(R.id.gift1);
        gift2 = findViewById(R.id.gift2);
        gift3 = findViewById(R.id.gift3);
        gift4 = findViewById(R.id.gift4);
        gift5 = findViewById(R.id.gift5);
        gift6 = findViewById(R.id.gift6);

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);
        r6 = findViewById(R.id.r6);

        rr1 = findViewById(R.id.rr1);
        rr2 = findViewById(R.id.rr2);
        rr3 = findViewById(R.id.rr3);
        rr4 = findViewById(R.id.rr4);
        rr5 = findViewById(R.id.rr5);
        rr6 = findViewById(R.id.rr6);

        total_pickups = findViewById(R.id.total_pickups);


        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getNames = user.get(SessionManager.FULLNAME);
        urls = new Urls();

        heading.setText("Claim rewards");

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = r1.getText().toString().trim();
                String reward = rr1.getText().toString().trim();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Rewards.this);
                alertDialog2.setTitle("You are about to submit your claim to your reward");
                alertDialog2.setMessage("Please note that the admin can deny this request if he/she doesn't feel your requests are not up to the necessary standards");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) ->
                                sendRequestClaim(times,reward));
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = r2.getText().toString().trim();
                String reward = rr2.getText().toString().trim();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Rewards.this);
                alertDialog2.setTitle("You are about to submit your claim to your reward");
                alertDialog2.setMessage("Please note that the admin can deny this request if he/she doesn't feel your requests are not up to the necessary standards");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) ->
                                sendRequestClaim(times,reward));
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = r3.getText().toString().trim();
                String reward = rr3.getText().toString().trim();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Rewards.this);
                alertDialog2.setTitle("You are about to submit your claim to your reward");
                alertDialog2.setMessage("Please note that the admin can deny this request if he/she doesn't feel your requests are not up to the necessary standards");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) ->
                                sendRequestClaim(times,reward));
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = r4.getText().toString().trim();
                String reward = rr4.getText().toString().trim();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Rewards.this);
                alertDialog2.setTitle("You are about to submit your claim to your reward");
                alertDialog2.setMessage("Please note that the admin can deny this request if he/she doesn't feel your requests are not up to the necessary standards");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) ->
                                sendRequestClaim(times,reward));
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = r5.getText().toString().trim();
                String reward = rr5.getText().toString().trim();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Rewards.this);
                alertDialog2.setTitle("You are about to submit your claim to your reward");
                alertDialog2.setMessage("Please note that the admin can deny this request if he/she doesn't feel your requests are not up to the necessary standards");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) ->
                                sendRequestClaim(times,reward));
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String times = r6.getText().toString().trim();
                String reward = rr6.getText().toString().trim();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Rewards.this);
                alertDialog2.setTitle("You are about to submit your claim to your reward");
                alertDialog2.setMessage("Please note that the admin can deny this request if he/she doesn't feel your requests are not up to the necessary standards");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) ->
                                sendRequestClaim(times,reward));
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        /*check to see if the user ever claimed any of these rewards*/
        loadOrderNo();
        checkFirst();
    }

    private void checkFirst() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.CHECKFIRST_REWARD,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {

                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);

                                String found = inputsObjects.getString("requested_reward");

                                if (found.equals("20000 air time")) {
                                    gift1.setBackgroundResource(R.color.purple_700);
//                                    c1.setBackgroundResource(R.color.white);
//                                    r1.setBackgroundResource(R.color.white);
//                                    rr1.setBackgroundResource(R.color.white);
                                    c1.setText("Reward claimed");
                                    c1.setTextColor(ContextCompat.getColor(this, R.color.white));
                                    rr1.setTextColor(ContextCompat.getColor(this, R.color.white));
                                    r1.setTextColor(ContextCompat.getColor(this, R.color.white));
                                    c1.setClickable(false);
                                } else if (found.equals("8 GB data")) {
                                    gift2.setBackgroundResource(R.color.purple_700);
                                    c2.setBackgroundResource(R.color.white);
                                    r2.setBackgroundResource(R.color.white);
                                    rr2.setBackgroundResource(R.color.white);
                                    c2.setText("Reward claimed");
                                } else if (found.equals("4G MIFI")) {
                                    gift3.setBackgroundResource(R.color.purple_700);
                                    c3.setBackgroundResource(R.color.white);
                                    r3.setBackgroundResource(R.color.white);
                                    rr3.setBackgroundResource(R.color.white);
                                    c3.setText("Reward claimed");
                                } else if (found.equals("Shopping vouchers")) {
                                    gift4.setBackgroundResource(R.color.purple_700);
                                    c4.setBackgroundResource(R.color.white);
                                    r4.setBackgroundResource(R.color.white);
                                    rr4.setBackgroundResource(R.color.white);
                                    c4.setText("Reward claimed");
                                } else if (found.equals("Full gas tank")) {
                                    gift5.setBackgroundResource(R.color.purple_700);
                                    c5.setBackgroundResource(R.color.white);
                                    r5.setBackgroundResource(R.color.white);
                                    rr5.setBackgroundResource(R.color.white);
                                    c5.setText("Reward claimed");
                                } else if (found.equals("Lexus Car")) {
                                    gift6.setBackgroundResource(R.color.purple_700);
                                    c6.setBackgroundResource(R.color.white);
                                    c6.setText("Reward claimed");
                                    r6.setBackgroundResource(R.color.white);
                                    rr6.setBackgroundResource(R.color.white);
                                }

                            }

                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "ee " +e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "ee " +error.toString(), Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadOrderNo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.COUNT_ORDERS,
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

                                /*check to see if the user has the required pickup requests*/
                                String d = total_pickups.getText().toString().trim();
                                int cc = Integer.parseInt(d);

                                if (cc == 10) {
                                    c1.setVisibility(View.VISIBLE);
                                } else if (cc == 20) {
                                    c2.setVisibility(View.VISIBLE);
                                } else if (cc == 30) {
                                    c3.setVisibility(View.VISIBLE);
                                } else if (cc == 40) {
                                    c4.setVisibility(View.VISIBLE);
                                } else if (cc == 50) {
                                    c5.setVisibility(View.VISIBLE);
                                } else if (cc == 60) {
                                    c6.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                    } catch (JSONException e) {
                        total_pickups.setText("Something went wrong, please try again");
                    }
                }, error -> {
            total_pickups.setText("Something went wrong, please check your connection and try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void sendRequestClaim(String times, String reward) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.CLAIM_REWARD,

                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Upload was successful", Toast.LENGTH_SHORT).show();

                            ss_card = new Dialog(this);
                            ss_card.setContentView(R.layout.right);
                            CardView dismiss = ss_card.findViewById(R.id.ss_card);
                            TextView success_type = ss_card.findViewById(R.id.tx_success);

                            success_type.setText("Request was sent successful");
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent aa = new Intent(Rewards.this, MainActivity.class);
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
//                        Toast.makeText(SendPickUp.this, "failed to upload your book'cover and summary, please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                        ee_card = new Dialog(this);
                        ee_card.setContentView(R.layout.error);
                        CardView dismiss = ee_card.findViewById(R.id.ee_card);
                        TextView success_type = ee_card.findViewById(R.id.tx_success);

                        success_type.setText("failed to send your request, please try again");
                        dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    Intent as = new Intent(Rewards.this,MainActivity.class);
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
                    TextView success_type = ee_card.findViewById(R.id.tx_success);

                    success_type.setText("failed to send your request, please check your connection and try again");
                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                    Intent as = new Intent(Rewards.this,MainActivity.class);
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

                params.put("userid", getID);
                params.put("fullnames", getNames);
                params.put("reward", reward);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void back(View view) {
        startActivity(new Intent(Rewards.this, MainActivity.class));
        finish();
    }


}