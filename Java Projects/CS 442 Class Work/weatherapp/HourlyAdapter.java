package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyViewHolder> {
    private final List<Hourly> hourlyList;
    private final MainActivity mainActivity;

    public HourlyAdapter(List<Hourly> empList, MainActivity ma) {
        this.hourlyList = empList;
        mainActivity = ma;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_entry, parent, false);
        return new HourlyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        Hourly hourly = hourlyList.get(position);
        holder.day.setText(hourly.getDay());
        holder.time.setText(hourly.getTime());
        try {
            holder.icon.setImageResource(mainActivity.getResources().getIdentifier(hourly.getIcon(), "drawable", mainActivity.getPackageName()));
        } catch (Exception e) {}
        holder.temp.setText(hourly.getTemp());
        holder.cond.setText(hourly.getConditions());
    }

    @Override
    public int getItemCount() { return hourlyList.size(); }
}
