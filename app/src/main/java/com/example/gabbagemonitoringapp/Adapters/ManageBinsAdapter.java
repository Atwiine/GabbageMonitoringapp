package com.example.gabbagemonitoringapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Admin.BinDetails;
import com.example.gabbagemonitoringapp.Admin.ManageBins;
import com.example.gabbagemonitoringapp.Models.ManageBinsModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManageBinsAdapter extends RecyclerView.Adapter<ManageBinsAdapter.AuthorViewHolder> {
    Context context;
    List<ManageBinsModel> mData;
    Urls urls;
    SessionManager sessionManager;
    String getID;
    Dialog ss_card, ee_card;

    public ManageBinsAdapter(Context context, List<ManageBinsModel> mData) {
        this.context = context;
        this.mData = mData;
        ss_card = new Dialog(context);
        ee_card = new Dialog(context);
        urls = new Urls();
        sessionManager = new SessionManager(context);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_manage_bins, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {



        ManageBinsModel authorModel = mData.get(position);
        holder.bin_number.setText(mData.get(position).getBinNumber());
        holder.bin_location.setText(mData.get(position).getLocation());
        holder.date_ordered.setText(mData.get(position).getDate());
        holder.id.setText(mData.get(position).getId());

        holder.edit_bin.setOnClickListener(view -> {
            Intent request = new Intent(context, BinDetails.class);
            request.putExtra("bNumber", mData.get(position).getBinNumber());
            request.putExtra("bLocation", mData.get(position).getLocation());
            request.putExtra("date", mData.get(position).getDate());
            request.putExtra("id", mData.get(position).getId());
            request.putExtra("last_inspection", mData.get(position).getInspectionDate());
            request.putExtra("condition_after_inspection", mData.get(position).getInspectionCondition());
            request.putExtra("status", mData.get(position).getstatus());
            request.putExtra("longitude", mData.get(position).getLongitude());
            request.putExtra("latitude", mData.get(position).getLatitude());
            context.startActivity(request);
        });

        //            deleting options
        holder.delete_bin.setOnClickListener(v -> {
            final String bid = holder.id.getText().toString();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Delete bin")
                    .setMessage("Are you sure you want to delete this bin permanently?")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_warning)
                    .setPositiveButton("YES", (dialog, which) -> {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.DELETE_BIN,
                                response -> {
                                    try {
                                        Log.i("tagconvertstr", "[" + response + "]");
                                        JSONObject object = new JSONObject(response);
                                        String success = object.getString("success");
                                        if (success.equals("1")) {
                                            Log.i("tagconvertstr", "[" + response + "]");

                                            ss_card = new Dialog(context);
                                            ss_card.setContentView(R.layout.right);
                                            CardView dismiss = ss_card.findViewById(R.id.ss_card);
                                            TextView success_type = ss_card.findViewById(R.id.tx_success);

                                            success_type.setText("Bin was deleted successfully");
                                            dismiss.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(context, ManageBins.class);
                                                    context.startActivity(intent);
                                                    ((Activity) context).finish();
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
//                                        Toast.makeText(context, "Product not deleted, please try again " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        ee_card = new Dialog(context);
                                        ee_card.setContentView(R.layout.error);
                                        CardView dismiss = ee_card.findViewById(R.id.ee_card);
                                        TextView success_type = ee_card.findViewById(R.id.tx_error);

                                        success_type.setText("Bin not deleted, please try again");
                                        dismiss.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                    Intent as = new Intent(context, ManageBins.class);
                                                context.startActivity(as);
                                                ((Activity) context).finish();
                                                ee_card.dismiss();
                                            }
                                        });

                                        ee_card.setCancelable(false);
                                        ee_card.setCanceledOnTouchOutside(false);
                                        Objects.requireNonNull(ee_card.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        ee_card.show();

                                    }
                                }, error -> {
                            dialog.dismiss();
                            ee_card = new Dialog(context);
                            ee_card.setContentView(R.layout.error);
                            CardView dismiss = ee_card.findViewById(R.id.ee_card);
                            TextView success_type = ee_card.findViewById(R.id.tx_error);

                            success_type.setText("Bin not deleted, please check your network and try again" + error.toString());
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent as = new Intent(context, ManageBins.class);
                                    context.startActivity(as);
                                    ((Activity) context).finish();
                                    ee_card.dismiss();
                                }
                            });

                            ee_card.setCancelable(false);
                            ee_card.setCanceledOnTouchOutside(false);
                            Objects.requireNonNull(ee_card.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            ee_card.show();

                        }) {

                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
//                                params.put("userid", getID);
                                params.put("id", bid);
                                return params;

                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                    })
                    .setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
            //Creating dialog box
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        });



        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView bin_number, bin_location, date_ordered, id,last_inspection,
                condition_after_inspection,status,latitude,longitude;
        CardView balance_card;
        MaterialCardView edit_bin,delete_bin;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            bin_number = itemView.findViewById(R.id.bin_number);
            bin_location = itemView.findViewById(R.id.bin_location);
            date_ordered = itemView.findViewById(R.id.date_ordered);
            balance_card = itemView.findViewById(R.id.balance_card);
            id = itemView.findViewById(R.id.id);
            edit_bin = itemView.findViewById(R.id.edit_bin);
            last_inspection = itemView.findViewById(R.id.last_inspection);
            condition_after_inspection = itemView.findViewById(R.id.condition_after_inspection);
            status = itemView.findViewById(R.id.status);
            delete_bin = itemView.findViewById(R.id.delete_bin);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);





        }
    }
}
