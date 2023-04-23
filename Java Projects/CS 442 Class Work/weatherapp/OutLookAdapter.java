package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OutLookAdapter extends RecyclerView.Adapter<OutLookViewHolder> {
    private final List<OutLook> outLookList;
    private final DailyForecastActivity dailyAct;

    public OutLookAdapter(List<OutLook> empList, DailyForecastActivity da) {
        this.outLookList = empList;
        dailyAct = da;
    }

    @NonNull
    @Override
    public OutLookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outlook_entry, parent, false);
        return new OutLookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutLookViewHolder holder, int position) {
        OutLook outLook = outLookList.get(position);
        holder.date.setText(outLook.getDate());
        holder.tempmaxmin.setText(outLook.getMaxTemp() + " / " + outLook.getMinTemp()); // do max and min
        holder.description.setText(outLook.getDescription());
        holder.percipprob.setText("(" + outLook.getPrecipProb() + "% precip.)");
        holder.uvindex.setText("UV Index: " + outLook.getUV());
        holder.morning.setText(outLook.getMorningTemp());
        holder.afternoon.setText(outLook.getAfternoonTemp());
        holder.evening.setText(outLook.getEveningTemp());
        holder.night.setText(outLook.getNightTemp());
        try {
            holder.icon.setImageResource(dailyAct.getResources().getIdentifier(outLook.getIcon(), "drawable", dailyAct.getPackageName()));
        } catch (Exception e) {}

    }

    @Override
    public int getItemCount() {
        return outLookList.size();
    }
}
