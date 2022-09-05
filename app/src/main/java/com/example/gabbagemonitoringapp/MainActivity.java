package com.example.gabbagemonitoringapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gabbagemonitoringapp.Preferences.SettingsActivity;
import com.example.gabbagemonitoringapp.Trash.Review;
import com.example.gabbagemonitoringapp.Trash.Rewards;
import com.example.gabbagemonitoringapp.Trash.SendPickUp;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView user_name,address;
    Dialog settings_popup, error_message, no_message,no_internet;
    ImageView reader_dp;
    SessionManager sessionManager;
    Urls urls;
    String getTYPE,getName,getID;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings_popup = new Dialog(this);

        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getName = user.get(SessionManager.USERNAME);
        getTYPE = user.get(SessionManager.TYPE);
        urls = new Urls();

//        if (!getTYPE.equals("User")){
//            startActivity(new Intent(MainActivity.this, LoginSecond.class));
//            finish();
//        }

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ){
            connected = true;

        }else {
            connected = false;
            no_internet = new Dialog(this);
            no_internet.setContentView(R.layout.no_internet);
            MaterialCardView no_conn = no_internet.findViewById(R.id.no_conn);

            no_conn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    System.exit(0);
                    no_internet.dismiss();
                }
            });

            no_internet.setCancelable(false);
            no_internet.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(no_internet.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            no_internet.show();
        }

        user_name = findViewById(R.id.user_name);
        user_name.setText(getName);

    }


    public void openCollect(View view) {
startActivity(new Intent(MainActivity.this, SendPickUp.class));
    }

    public void openRecords(View view) {
        startActivity(new Intent(MainActivity.this, Review.class));
    }

    public void openRwards(View view) {
        startActivity(new Intent(MainActivity.this, Rewards.class));
    }
    public void openMenu(View view) {

        SettingsActivity settingsActivity = new SettingsActivity();
        settingsActivity.show(getSupportFragmentManager(),
                "ModalBottomSheet");

    }
}