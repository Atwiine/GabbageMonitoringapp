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

import com.bumptech.glide.Glide;
import com.example.gabbagemonitoringapp.Models.ReviewModel;
import com.example.gabbagemonitoringapp.R;
import com.example.gabbagemonitoringapp.Urls.SessionManager;
import com.example.gabbagemonitoringapp.Urls.Urls;

import java.util.HashMap;
import java.util.List;

public class ReportClientsAdapter extends RecyclerView.Adapter<ReportClientsAdapter.AuthorViewHolder> {
    Context context;
    List<ReviewModel> mData;
    Urls urls;
    SessionManager sessionManager;
    String getID;

    public ReportClientsAdapter(Context context, List<ReviewModel> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_review, null, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, final int position) {

        ReviewModel authorModel = mData.get(position);
        holder.bin_number.setText(mData.get(position).getTitle());
        holder.bin_location.setText(mData.get(position).getPreview());
        holder.date_ordered.setText(mData.get(position).getAuthor());
        holder.id.setText(mData.get(position).getId());
        holder.status.setText(mData.get(position).getStatus());

        holder.balance_card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        String imageUrl = urls.https + "bin_images/" + authorModel.getImage();
        try {

            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.product_image);
        } catch (Exception e) {
            e.printStackTrace();
        }


        sessionManager = new SessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        String getCardID = holder.id.getText().toString();
        String ccee = holder.status.getText().toString();

        if (ccee.equals("pending")){
            holder.status.setBackgroundResource(R.color.red);
        }else  if (ccee.equals("Done")){
            holder.status.setText("Order worked on");
        }

        /*check to see if the id on the card is the same as the same id in the session manager then
         * hide the damn card if not show the card*/

        if (getID.matches(getCardID)) {
            holder.balance_card.setVisibility(View.GONE);
        } else {
            holder.balance_card.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView bin_number, bin_location, date_ordered, id,status;
        CardView balance_card, order;
        ImageView product_image;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            bin_number = itemView.findViewById(R.id.bin_number);
            bin_location = itemView.findViewById(R.id.bin_location);
            date_ordered = itemView.findViewById(R.id.date_ordered);
            balance_card = itemView.findViewById(R.id.balance_card);
            id = itemView.findViewById(R.id.id);
            product_image = itemView.findViewById(R.id.product_image);
            status = itemView.findViewById(R.id.status);


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
