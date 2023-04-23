package com.example.androidnotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView label;
    TextView date;
    TextView description;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        label = itemView.findViewById(R.id.note_label);
        date = itemView.findViewById(R.id.note_time);
        description = itemView.findViewById(R.id.note_description);

    }
}
