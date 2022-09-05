package com.example.gabbagemonitoringapp.Registrations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.gabbagemonitoringapp.Admin.MainAdmin;
import com.example.gabbagemonitoringapp.MainActivity;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import java.util.HashMap;

public class CheckFirst extends AppCompatActivity {

    Urls urls;
    SessionManager sessionManager;
    String getID ="", cate = "";
    TextView ccc;
    CardView button;
EditText check_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_first);

        sessionManager = new SessionManager(getApplicationContext());

//        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        String cate = user.get(SessionManager.TYPE);

        urls = new Urls();

        check_phone = findViewById(R.id.check_phone);
        button = findViewById(R.id.button);
        ccc = findViewById(R.id.ccc);
        ccc.setText(cate);
        String ok = ccc.getText().toString();

//        if (TextUtils.isEmpty(cate)){

        if (ok.contains("Admin")) {
            startActivity(new Intent(CheckFirst.this, MainAdmin.class));
            finish();
        } else if (ok.contains("User")) {
            startActivity(new Intent(CheckFirst.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(CheckFirst.this, LoginSecond.class));
            finish();
        }

    }

}