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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabbagemonitoringapp.Admin.ManageRewards;
import com.example.gabbagemonitoringapp.Models.ManageRewardsModel;
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

public class ManageRewardsAdapter extends RecyclerView.Adapter<ManageRewardsAdapter.AuthorViewHolder> {
    Context context;
    List<ManageRewardsModel> mData;
    Urls urls;
    SessionManager sessionManager;
    String getID;
    Dialog ss_card, ee_card;

    public ManageRewardsAdapter(Context context, List<ManageRewardsModel> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_manage_rewards, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {

        ManageRewardsModel authorModel = mData.get(position);
        holder.names.setText(mData.get(position).getNames());
        holder.address.setText(mData.get(position).getAddress());
        holder.status.setText(mData.get(position).getStatus());
        holder.id.setText(mData.get(position).getId());
        holder.total_pickups.setText(mData.get(position).getNumberPickups());
        holder.requested_reward.setText(mData.get(position).getRequested_reward());

        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        String cc = holder.status.getText().toString();
        if (!cc.equals("Denied") && !cc.equals("Confirmed")){
        if (cc.equals("Pending")){
            holder.linear_status.setBackgroundColor(Color.parseColor("#D30000"));
            holder.side_alert.setBackgroundColor(Color.parseColor("#D30000"));
        }
        }else if (cc.equals("Denied")){
            holder.optons_linear.setVisibility(View.GONE);
            holder.linear_status.setBackgroundColor(Color.parseColor("#D30000"));
            holder.side_alert.setBackgroundColor(Color.parseColor("#D30000"));
        }
        else {
            holder.optons_linear.setVisibility(View.GONE);
            holder.linear_status.setBackgroundColor(Color.parseColor("#02B7A5"));
            holder.side_alert.setBackgroundColor(Color.parseColor("#02B7A5"));
        }

        holder.delete_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.delete_bin.setVisibility(View.GONE);
                holder.linear_deny.setVisibility(View.VISIBLE);
            }
        });
        /*deny request*/
        holder.send_deny_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bid = holder.id.getText().toString();
                final String bre = holder.why_deny.getText().toString();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Deny reward request")
                        .setMessage("Are you sure you want to deny this request?")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_warning)
                        .setPositiveButton("YES", (dialog, which) -> {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.DENY_REWARD_REQUEST,
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

                                                success_type.setText("Request was denied successfully");
                                                dismiss.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent(context, ManageRewards.class);
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
                                        Toast.makeText(context, "Product not deleted, please try again " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            ee_card = new Dialog(context);
                                            ee_card.setContentView(R.layout.error);
                                            CardView dismiss = ee_card.findViewById(R.id.ee_card);
                                            TextView success_type = ee_card.findViewById(R.id.tx_error);

                                            success_type.setText("Request not denied, please try again");
                                            dismiss.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent as = new Intent(context, ManageRewards.class);
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
                                Toast.makeText(context, "Product not deleted, please try again " + error.getMessage(), Toast.LENGTH_LONG).show();

                                ee_card = new Dialog(context);
                                ee_card.setContentView(R.layout.error);
                                CardView dismiss = ee_card.findViewById(R.id.ee_card);
                                TextView success_type = ee_card.findViewById(R.id.tx_error);

                                success_type.setText("Request not denied, please check your network and try again" + error.toString());
                                dismiss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent as = new Intent(context, ManageRewards.class);
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
                                params.put("reason", bre);
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
            }
        });

        /*confirm request*/
        holder.edit_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bid = holder.id.getText().toString();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Confirm reward request")
                        .setMessage("Are you sure you want to confirm this request?")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_warning)
                        .setPositiveButton("YES", (dialog, which) -> {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.CONFIRM_REWARD_REQUEST,
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

                                                success_type.setText("Request was confirmed successfully");
                                                dismiss.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent(context, ManageRewards.class);
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

                                            success_type.setText("Request not confirmed, please try again");
                                            dismiss.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent as = new Intent(context, ManageRewards.class);
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

                                success_type.setText("Request not confirmed, please check your network and try again" + error.toString());
                                dismiss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent as = new Intent(context, ManageRewards.class);
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
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView names, address, status, id,total_pickups,requested_reward;
        CardView balance_card, order;
        ImageView product_image;
        LinearLayout linear_status,linear_deny,optons_linear;
        RelativeLayout side_alert;
        MaterialCardView edit_bin,delete_bin,send_deny_reason;
EditText why_deny;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.names);
            address = itemView.findViewById(R.id.address);
            status = itemView.findViewById(R.id.status);
            balance_card = itemView.findViewById(R.id.balance_card);
            id = itemView.findViewById(R.id.id);
            total_pickups = itemView.findViewById(R.id.total_pickups);
            side_alert = itemView.findViewById(R.id.side_alert);
            linear_status = itemView.findViewById(R.id.linear_status);
            edit_bin = itemView.findViewById(R.id.edit_bin);
            delete_bin = itemView.findViewById(R.id.delete_bin);
            send_deny_reason = itemView.findViewById(R.id.send_deny_reason);
            linear_deny = itemView.findViewById(R.id.linear_deny);
            why_deny = itemView.findViewById(R.id.why_deny);
            optons_linear = itemView.findViewById(R.id.optons_linear);
            requested_reward = itemView.findViewById(R.id.requested_reward);


//            balance_card.setOnClickListener(view -> {
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
