package com.example.gabbagemonitoringapp.Preferences;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gabbagemonitoringapp.R;


public class About_us extends AppCompatActivity {

    ImageView arrow;
    Dialog about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

//        about = new Dialog(this);
//        findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clicked_btn("https://info@yitug.org");
//            }
//        });
//
//        findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clicked_btn("https://www.facebook.com/Empower Youth in Technology");
////                FacebookPage facebookPage = new FacebookPage();
////                facebookPage.show(getSupportFragmentManager(),
////                        "ModalBottomSheet");
//
//            }
//        });


//        findViewById(R.id.web).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clicked_btn("https://www.yitug.org");
//            }
//        });


        arrow = findViewById(R.id.back);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(About_us.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    public void clicked_btn(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void openAbout(View view) {
//
//        about.setContentView(R.layout.about_dialog);
//       CardView ok_about = about.findViewById(R.id.ok_about);
//        ok_about.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                about.dismiss();
//            }
//        });
//        Objects.requireNonNull(about.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        about.show();
//    }
}
