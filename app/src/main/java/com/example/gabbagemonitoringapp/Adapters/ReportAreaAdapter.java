package com.example.gabbagemonitoringapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gabbagemonitoringapp.Models.ReportAreaModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import java.util.List;

public class ReportAreaAdapter extends RecyclerView.Adapter<ReportAreaAdapter.AuthorViewHolder> {
    Context context;
    List<ReportAreaModel> mData;
    Urls urls;
    SessionManager sessionManager;
    String getID;

    public ReportAreaAdapter(Context context, List<ReportAreaModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
        sessionManager = new SessionManager(context);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_area, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {

        ReportAreaModel authorModel = mData.get(position);
        holder.number_pickups.setText(mData.get(position).getNo_pickups());
        holder.bin_location.setText(mData.get(position).getName());
//        holder.id.setText(mData.get(position).getId());

        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView number_pickups, bin_location, id;
        CardView balance_card, order;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            number_pickups = itemView.findViewById(R.id.number_pickups);
            bin_location = itemView.findViewById(R.id.bin_location);
            balance_card = itemView.findViewById(R.id.balance_card);
//            id = itemView.findViewById(R.id.id);

        }
    }
}
