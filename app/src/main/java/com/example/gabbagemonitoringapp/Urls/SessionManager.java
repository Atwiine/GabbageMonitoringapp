package com.example.gabbagemonitoringapp.Urls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gabbagemonitoringapp.Registrations.CheckFirst;
import com.example.gabbagemonitoringapp.Registrations.LoginSecond;

import java.util.HashMap;

public class SessionManager {
    public static final String CONTACT = "CONTACT";
    public static final String FULLNAME = "FULLNAME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String BNUMBER = "BNUMBER";
    public static final String BLATITUDE = "BLATITUDE";
    public static final String BLONGITUDE = "BLONGITUDE";
    public static final String ID = "ID";
    public static final String TYPE = "TYPE";
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public SharedPreferences.Editor editor;
    public Context context;
    SharedPreferences sharedPreferences;
    int PRIVATE_MODE = 0;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String username, String fullname, String id,String password, String type,
                              String bnumber,String blatitude, String blongitude
    ) {
        editor.putBoolean(LOGIN, true);
        editor.putString(TYPE, type);
        editor.putString(USERNAME, username);
        editor.putString(FULLNAME, fullname);
        editor.putString(PASSWORD, password);
        editor.putString(ID, id);
        editor.putString(BNUMBER, bnumber);
        editor.putString(BLATITUDE, blatitude);
        editor.putString(BLONGITUDE, blongitude);
        editor.apply();

    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginSecond.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(i);
//            ((tryitout) context).finish();
        }
        else {
                Intent i = new Intent(context, CheckFirst.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(FULLNAME, sharedPreferences.getString(FULLNAME, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(TYPE, sharedPreferences.getString(TYPE, null));

        user.put(BNUMBER, sharedPreferences.getString(BNUMBER, null));
        user.put(BLATITUDE, sharedPreferences.getString(BLATITUDE, null));
        user.put(BLONGITUDE, sharedPreferences.getString(BLONGITUDE, null));

        return user;
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginSecond.class);
        context.startActivity(i);
    }

}
