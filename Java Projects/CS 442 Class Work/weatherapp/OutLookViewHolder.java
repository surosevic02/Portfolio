package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class OutLookViewHolder extends RecyclerView.ViewHolder {
    TextView date;
    TextView tempmaxmin;
    TextView percipprob;
    TextView uvindex;
    TextView description;
    TextView morning;
    TextView afternoon;
    TextView evening;
    TextView night;
    ImageView icon;

    public OutLookViewHolder(View v) {
        super(v);
        date = v.findViewById(R.id.text_date);
        tempmaxmin = v.findViewById(R.id.text_max_min);
        percipprob = v.findViewById(R.id.text_percip);
        uvindex = v.findViewById(R.id.text_uv);
        description = v.findViewById(R.id.text_desc);
        morning = v.findViewById(R.id.text_morning);
        afternoon = v.findViewById(R.id.text_afternoon);
        evening = v.findViewById(R.id.text_evening);
        night = v.findViewById(R.id.text_night);
        icon = v.findViewById(R.id.imageView);
    }

}
