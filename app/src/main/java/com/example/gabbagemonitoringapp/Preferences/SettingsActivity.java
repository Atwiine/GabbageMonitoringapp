package com.example.gabbagemonitoringapp.Preferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Registrations.update;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SettingsActivity extends BottomSheetDialogFragment {

    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        sessionManager = new SessionManager(getActivity());

        View v = inflater.inflate(R.layout.activity_settings,
                container, false);

        LinearLayout linear_account = v.findViewById(R.id.linear_account);
        LinearLayout appinfo = v.findViewById(R.id.appinfo);
        LinearLayout about = v.findViewById(R.id.about);
        LinearLayout linear_logout = v.findViewById(R.id.linear_logout);
        LinearLayout help = v.findViewById(R.id.help);

        linear_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update.class);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
        appinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), app_information.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About_us.class);
                startActivity(intent);
            }
        });
        linear_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        getActivity());
                alertDialog2.setTitle("Confirm to proceed");
                alertDialog2.setMessage("Are you sure you want to logout");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) -> sessionManager.logout());
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        return v;
    }
}