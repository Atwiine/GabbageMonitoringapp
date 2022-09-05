package com.example.gabbagemonitoringapp.Trash;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.MainActivity;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SendPickUp extends AppCompatActivity {

    MaterialCardView add_product, upload_image;
    ImageView image_preview;
    EditText bin_number, bin_location, book_author_name;
    Urls urls;
    SessionManager sessionManager;
    String getID, getName,getBnumber,getLat,getLong;
    Dialog ss_card, ee_card;
    private Bitmap bitmap;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
TextView showcoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_pick_up);

        ss_card = new Dialog(this);
        ee_card = new Dialog(this);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getName = user.get(SessionManager.FULLNAME);
        getBnumber = user.get(SessionManager.BNUMBER);
        getLat = user.get(SessionManager.BLATITUDE);
        getLong = user.get(SessionManager.BLONGITUDE);
        urls = new Urls();

        bin_location = findViewById(R.id.bin_location);
        image_preview = findViewById(R.id.image_preview);
        add_product = findViewById(R.id.add_product);
        upload_image = findViewById(R.id.upload_image);
        book_author_name = findViewById(R.id.book_author_name);
        bin_number = findViewById(R.id.bin_number);
        showcoo = findViewById(R.id.showcoo);

        bin_number.setText(getBnumber);


        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions(SendPickUp.this)){
                    chooseImage(SendPickUp.this);
                };
            }
        });

        //handle permissions

    }


    /*
     * HANDLE UPLOAD IMAGES FROM GALLERY
     * */
    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), 1);
    }


//    // Function to check and request permission.
//    public void checkPermission(String permission, int requestCode) {
//        if (ContextCompat.checkSelfPermission(SendPickUp.this, permission)
//                == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(SendPickUp.this,
//                    new String[]{permission},
//                    requestCode);
//        } else {
//            Toast.makeText(SendPickUp.this,
//                    "Permission already granted",
//                    Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }



        // function to let's the user to choose image from camera or gallery
        private void chooseImage(Context context){
            final CharSequence[] optionsMenu = {"Take Photo", "Exit" }; // create a menuOption Array
            // create a dialog for showing the optionsMenu
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // set the items in builder
            builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(optionsMenu[i].equals("Take Photo")){
                        // Open the camera and get the photo
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                    }
//                    else if(optionsMenu[i].equals("Choose from Gallery")){
//                        // choose from  external storage
////                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                        startActivityForResult(pickPhoto , 1);
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1    );
//                    }
                    else if (optionsMenu[i].equals("Exit")) {
                        dialogInterface.dismiss();
                    }
                }
            });
            builder.show();
        }
        // function to check permission
        public static boolean checkAndRequestPermissions(final Activity context) {
            int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded
                        .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(context, listPermissionsNeeded
                                .toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;
        }
        // Handled permission Result
        @Override
        public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_ID_MULTIPLE_PERMISSIONS:
                    if (ContextCompat.checkSelfPermission(SendPickUp.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(),
                                        "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                                .show();
                    } else if (ContextCompat.checkSelfPermission(SendPickUp.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(),
                                "FlagUp Requires Access to Your Storage.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        chooseImage(SendPickUp.this);
                    }
                    break;
            }
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode != RESULT_CANCELED) {
                switch (requestCode) {
                    case 0:
                        if (resultCode == RESULT_OK && data != null) {
                            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                            image_preview.setVisibility(View.VISIBLE);
                            image_preview.setImageBitmap(selectedImage);
                            getStringImage(bitmap);
                        }
                        break;
                    case 1:
                        if (resultCode == RESULT_OK && data != null) {
                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                                if (cursor != null) {
                                    cursor.moveToFirst();
                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    String picturePath = cursor.getString(columnIndex);
                                    image_preview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                    cursor.close();
                                }
                            }
                        }
                        break;
                }
            }
        }



//            @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri filePath = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                image_preview.setVisibility(View.VISIBLE);
//                image_preview.setImageBitmap(bitmap);
//                getStringImage(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }


    public void submitOrder(View view) {

        /*submitting part*/

        String bt = bin_number.getText().toString().trim();
        String bs = bin_location.getText().toString().trim();

        if (!bt.isEmpty() && !bs.isEmpty() ) {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    SendPickUp.this);
            alertDialog2.setTitle("Confirm before submitting");
            alertDialog2.setMessage("Make sure you double check before uploading your trash pick up form");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) ->
                            sendProduct(getStringImage(bitmap)));
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        } else {
            Toast.makeText(SendPickUp.this, "All fields are required", Toast.LENGTH_SHORT).show();

        }

    }


    private void sendProduct(final String photo) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        String bt = bin_number.getText().toString().trim();
        String bs = bin_location.getText().toString().trim();
        String ba = book_author_name.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPLOAD_ORDER,

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
                                    Intent aa = new Intent(SendPickUp.this, Review.class);
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

                    success_type.setText("failed to send your request, please check your connection and try again");
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
                params.put("username", getName);
                params.put("binNo", bt);
                params.put("binLocation", bs);
                params.put("image", photo);
                params.put("latitude", getLat);
                params.put("longitude", getLong);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = ((BitmapDrawable) image_preview.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public void back(View view) {
        startActivity(new Intent(SendPickUp.this, MainActivity.class));
        finish();
    }

}