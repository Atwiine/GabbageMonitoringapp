package com.example.gabbagemonitoringapp.Preferences;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HelpActivity extends AppCompatActivity {

    ImageView a;
    CardView call, send_message_help;
    BottomNavigationView bottomNavigationView;
    SessionManager sessionManager;
    LinearLayout linear_help_message;
    MaterialCardView send_inquiry;
    EditText inquiry_provided;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        urls = new Urls();

        inquiry_provided = findViewById(R.id.inquiry_provided);
        send_inquiry = findViewById(R.id.send_inquiry);
        linear_help_message = findViewById(R.id.linear_help_message);
        call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUs();
            }
        });

        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        findViewById(R.id.send_message_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_help_message.setVisibility(View.VISIBLE);

            }
        });


        send_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = inquiry_provided.getText().toString().trim();
                if (!check.equals("")) {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            HelpActivity.this);
                    alertDialog2.setTitle("Confirm to proceed");
                    alertDialog2.setMessage("Make sure you double check your message before sending");
                    alertDialog2.setIcon(R.drawable.ic_warning);
                    alertDialog2.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendHelpMess();
                                }
                            });
                    alertDialog2.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog2.show();
                } else {
                    inquiry_provided.setError("your message is required please");
                }
            }
        });


    }


    public void callUs() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:07000000"));
        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        startActivity(new Intent(HelpActivity.this,SettingsActivity.class));
        finish();
    }

    private void sendHelpMess() {
        final ProgressDialog dialog = new ProgressDialog(HelpActivity.this);
        dialog.setMessage("Please wait...");
        dialog.show();

        final String helpmessage = this.inquiry_provided.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.HELPMESSAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("success");
                            if (success.equals("1")) {
                                dialog.dismiss();
                                Toast.makeText(HelpActivity.this, "message sent successfully", Toast.LENGTH_LONG).show();
                                inquiry_provided.getText().clear();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HelpActivity.this, "message not sent successful, please try again ", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HelpActivity.this, "message not sent successful, please check your network and try again", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("message", helpmessage);
                params.put("userid", getID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
