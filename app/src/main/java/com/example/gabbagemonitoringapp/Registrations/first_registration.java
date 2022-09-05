package com.example.gabbagemonitoringapp.Registrations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Admin.MonitorUsers;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class first_registration extends AppCompatActivity {

    CardView reg , rules;
    EditText fullname, username, etPassword, confirm_etPassword,address_regist,bin_location_long,bin_location_lat
            ,etBinNumber;
    Urls urls;
    SessionManager sessionManager;
    Spinner cat;
    RelativeLayout show_dp;
    ImageView real_dp;
    private Bitmap bitmap;
    TextView already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_registration);


        sessionManager = new SessionManager(this);
        urls = new Urls();

        address_regist = findViewById(R.id.address_regist);
        already = findViewById(R.id.button17);
        fullname = findViewById(R.id.fullname_regist);
        username = findViewById(R.id.username_regist);

        reg = findViewById(R.id.reg);
        confirm_etPassword = findViewById(R.id.confirm_etPassword);
        show_dp = findViewById(R.id.show_dp);

//        bin_location_long = findViewById(R.id.bin_location_long);
//        bin_location_lat = findViewById(R.id.bin_location_lat);
        etBinNumber = findViewById(R.id.etBinNumber);


        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(first_registration.this, LoginSecond.class);
                startActivity(intent);
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name_val = fullname.getText().toString().trim();
                final String usernames = username.getText().toString().trim();
//                String pass = etPassword.getText().toString().trim();

                String add = address_regist.getText().toString().trim();
//                String btla = bin_location_lat.getText().toString().trim();&& !btla.isEmpty()&& !bslo.isEmpty()
//                String bslo = bin_location_long.getText().toString().trim();
                String bnum = etBinNumber.getText().toString().trim();


                    if (!full_name_val.isEmpty() && !usernames.isEmpty() && !add.isEmpty()
                             &&!bnum.isEmpty()) {

                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                                    first_registration.this);
                            alertDialog2.setTitle("Confirm to proceed to register");
                            alertDialog2.setMessage("Make sure you double check your registration details");
                            alertDialog2.setIcon(R.drawable.ic_warning);
                            alertDialog2.setPositiveButton("YES",
                                    (dialog, which) -> {
                                        Register();
                                    });
                            alertDialog2.setNegativeButton("NO",
                                    (dialog, which) -> dialog.cancel());
                            alertDialog2.show();

                    } else {
                        Toast.makeText(first_registration.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    }
            }

        });


    }




    private void Register() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        final String full_names = this.fullname.getText().toString().trim();
        final String usernn = this.username.getText().toString().trim();
        final String add = this.address_regist.getText().toString().trim();
//        String btla = bin_location_lat.getText().toString().trim();
//        String bslo = bin_location_long.getText().toString().trim();
        String bnum = etBinNumber.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.FIRSTREG_URL,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent aa = new Intent(first_registration.this, MonitorUsers.class);
                            startActivity(aa);
                            finish();

                        }if (success.equals("2")) {
                            Toast.makeText(getApplicationContext(),"Sorry username already taken, please choose another username and try again.",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registration was unsuccessful, please make sure you are assigning this user the correct bin number and try again", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Registration continues to fail, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), " try again" + error.toString(), Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", full_names);
                params.put("username", usernn);
                params.put("address", add);
//                params.put("latitude", btla);
//                params.put("longitude", bslo);
                params.put("bin_number", bnum);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void backLogin(View view) {
        startActivity(new Intent(first_registration.this,LoginSecond.class));
    }
}
