package com.example.gabbagemonitoringapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gabbagemonitoringapp.Models.MonitorUsersModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import java.util.List;

public class MonitorUsersAdapter extends RecyclerView.Adapter<MonitorUsersAdapter.AuthorViewHolder> {
    Context context;
    List<MonitorUsersModel> mData;
    Urls urls;
    SessionManager sessionManager;
    String getID;

    public MonitorUsersAdapter(Context context, List<MonitorUsersModel> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_monitor_users, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {

        MonitorUsersModel authorModel = mData.get(position);
        holder.names.setText(mData.get(position).getNames());
        holder.address.setText(mData.get(position).getAddress());
        holder.date_registration.setText(mData.get(position).getDate());
        holder.id.setText(mData.get(position).getId());
        holder.total_pickups.setText(mData.get(position).getNumberPickups());

        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView names, address, date_registration, id,total_pickups;
        CardView balance_card, order;
        ImageView product_image;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.names);
            address = itemView.findViewById(R.id.address);
            date_registration = itemView.findViewById(R.id.date_registration);
            balance_card = itemView.findViewById(R.id.balance_card);
            id = itemView.findViewById(R.id.id);
            total_pickups = itemView.findViewById(R.id.total_pickups);


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
