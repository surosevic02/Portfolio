package com.example.civiladvocacyapp;

import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PoliticianViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView nameParty;
    ImageView photo;

    public PoliticianViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.elemTitle);
        nameParty = itemView.findViewById(R.id.elemNameParty);
        photo = itemView.findViewById(R.id.elemPhoto);

    }
}
