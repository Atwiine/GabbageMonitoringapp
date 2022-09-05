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

import com.example.gabbagemonitoringapp.Models.ReportDaysModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import java.util.List;

public class ReportDaysAdapter extends RecyclerView.Adapter<ReportDaysAdapter.AuthorViewHolder> {
    Context context;
    List<ReportDaysModel> mData2;
    Urls urls;
    SessionManager sessionManager;

    public ReportDaysAdapter(Context context, List<ReportDaysModel> mData) {
        this.context = context;
        this.mData2 = mData;
        urls = new Urls();
        sessionManager = new SessionManager(context);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_days, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {

        ReportDaysModel authorModel = mData2.get(position);
        holder.number_pickups.setText(mData2.get(position).getNo_pickups());
        holder.datee.setText(mData2.get(position).getDate());
//        holder.id.setText(mData.get(position).getId());
        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

    }

    @Override
    public int getItemCount() {
        return mData2.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView number_pickups, datee, id;
        CardView balance_card, order;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            number_pickups = itemView.findViewById(R.id.number_pickups);
            datee = itemView.findViewById(R.id.datee);
            balance_card = itemView.findViewById(R.id.balance_card);
            id = itemView.findViewById(R.id.id);



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
