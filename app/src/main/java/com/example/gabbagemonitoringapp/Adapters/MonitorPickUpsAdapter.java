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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.gabbagemonitoringapp.Admin.ManageBins;
import com.example.gabbagemonitoringapp.Admin.MonitorPickUpOrders;
import com.example.gabbagemonitoringapp.Models.MonitorPickUpsModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MonitorPickUpsAdapter extends RecyclerView.Adapter<MonitorPickUpsAdapter.AuthorViewHolder> {
    Context context;
    List<MonitorPickUpsModel> mData;
    Urls urls;
    SessionManager sessionManager;
    String getID;
    Dialog ss_card, ee_card;

    public MonitorPickUpsAdapter(Context context, List<MonitorPickUpsModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
        ss_card = new Dialog(context);
        ee_card = new Dialog(context);
        sessionManager = new SessionManager(context);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_monitor_pick_ups, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {

        MonitorPickUpsModel authorModel = mData.get(position);
        holder.bin_number.setText(mData.get(position).getBin_number());
        holder.bin_location.setText(mData.get(position).getLocation());
        holder.date_ordered.setText(mData.get(position).getOrderdate());
        holder.id.setText(mData.get(position).getId());
        holder.status.setText(mData.get(position).getStatus());
        holder.order_from.setText(mData.get(position).getFrom());

        holder.latitude.setText(mData.get(position).getLatitude());
        holder.longitude.setText(mData.get(position).getLongitude());

        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        String imageUrl = urls.https + "bin_images/" + authorModel.getImage();
        try {

            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.product_image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String cc = holder.status.getText().toString();

        if (cc.equals("pending")) {
            holder.linear_status.setBackgroundColor(Color.parseColor("#D30000"));
        } else if (cc.equals("Done")) {
            holder.linear_status.setBackgroundColor(Color.parseColor("#02B7A5"));
            holder.updatestatus.setVisibility(View.GONE);
            holder.confirmed_order.setVisibility(View.VISIBLE);
        }


        //            confirming options
        holder.updatestatus.setOnClickListener(v -> {
            final String bid = holder.id.getText().toString();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Update bin")
                    .setMessage("Make sure the order has been worked on before confirming it")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_warning)
                    .setPositiveButton("YES", (dialog, which) -> {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_WORK_ORDER,
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

                                            success_type.setText("Order was updated successfully");
                                            dismiss.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(context, MonitorPickUpOrders.class);
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

                                        success_type.setText("Order was not updated , please try again");
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

                            success_type.setText("Order was not updated, please check your network and try again" + error.toString());
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

//handle the sending the coordinates to the maps
        holder.show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lat =  holder.latitude.getText().toString();
                String longi =  holder.longitude.getText().toString();
                Toast.makeText(context, "lat is " + lat + "long is " + longi, Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"plus need to add a map here" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView bin_number, bin_location, date_ordered, id, status,
                updatestatus, order_from,confirmed_order,longitude,latitude;
        CardView balance_card, order;
        ImageView product_image;
        LinearLayout linear_status,show_location;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            bin_number = itemView.findViewById(R.id.bin_number);
            bin_location = itemView.findViewById(R.id.bin_location);
            date_ordered = itemView.findViewById(R.id.date_ordered);
            balance_card = itemView.findViewById(R.id.balance_card);
            id = itemView.findViewById(R.id.id);
            product_image = itemView.findViewById(R.id.product_image);
            status = itemView.findViewById(R.id.status);
            updatestatus = itemView.findViewById(R.id.updatestatus);
            order_from = itemView.findViewById(R.id.order_from);
            linear_status = itemView.findViewById(R.id.linear_status);
            confirmed_order = itemView.findViewById(R.id.confirmed_order);
            show_location = itemView.findViewById(R.id.show_location);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);


//            updatestatus.setOnClickListener(view -> {
//                Intent request = new Intent(context, OtherBookDetails.class);
//                request.putExtra("title", mData.get(getAdapterPosition()).getTitle());
//                request.putExtra("preview", mData.get(getAdapterPosition()).getPreview());
//                request.putExtra("author", mData.get(getAdapterPosition()).getAuthor());
//                request.putExtra("id", mData.get(getAdapterPosition()).getId());
//                request.putExtra("image_url", urls.https + "cover_images/" + mData.get(getAdapterPosition()).getImage());
//                context.startActivity(request);
//            });


        }
    }
}
