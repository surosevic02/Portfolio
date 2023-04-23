package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HourlyViewHolder extends RecyclerView.ViewHolder {
    TextView day;
    TextView time;
    ImageView icon;
    TextView temp;
    TextView cond;

    public HourlyViewHolder(View v) {
        super(v);
        day = v.findViewById(R.id.he_day);
        time = v.findViewById(R.id.he_time);
        temp = v.findViewById(R.id.he_temp);
        cond = v.findViewById(R.id.he_desc);
        icon = v.findViewById(R.id.imageViewHe);
    }
}
