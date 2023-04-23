package com.example.civiladvocacyapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class PoliticianAdapter extends RecyclerView.Adapter<PoliticianViewHolder> {
    private final List<PoliticianObject> politicianList;
    private final MainActivity mainActivity;

    public PoliticianAdapter(List<PoliticianObject> empList, MainActivity ma) {
        this.politicianList = empList;
        mainActivity = ma;
    }

    @NonNull
    @Override
    public PoliticianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_element, parent, false);
        itemView.setOnClickListener(mainActivity);
        return new PoliticianViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliticianViewHolder holder, int position) {
        PoliticianObject politicianObject = politicianList.get(position);
        holder.title.setText(politicianObject.getTitle());
        holder.nameParty.setText(politicianObject.getName() + " (" + politicianObject.getParty() + ")");
        if (politicianObject.getPhoto().equals("")) {
            holder.photo.setImageResource(R.drawable.missing);
        } else if (!(politicianObject.getPhoto().equals(""))) {
                try {
                    Glide.with(mainActivity)
                            .load(politicianObject.getPhoto())
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).dontAnimate().into(holder.photo);
                } catch (Exception e) {
                    holder.photo.setImageResource(R.drawable.brokenimage);
                }
        }
        // Devise method for when the image doesn't exist
        //holder.photo.setImageResource(mainActivity.getResources().getIdentifier(politicianObject.getPhoto(), "drawable", mainActivity.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return politicianList.size();
    }
}
