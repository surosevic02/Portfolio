package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DailyForecastActivity extends AppCompatActivity {

    private final List<OutLook> outLookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OutLookAdapter oAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        recyclerView = findViewById(R.id.outlookRecycler);
        oAdapter = new OutLookAdapter(outLookList, this);

        recyclerView.setAdapter(oAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //outLookList.add(new OutLook("DATE", 1.0, 0.0, 50.0, 5, "DESC"));
        //oAdapter.notifyItemRangeInserted(0, outLookList.size());
        // CREATE THE LIST IN MAIN ACTIVITY AND HAVE IT PASSED HERE ... MODIFY VOLLEY TO MAKE THAT WORK
    }

    @Override
    protected void onStart() {
        ArrayList<OutLook> temp = (ArrayList<OutLook>) getIntent().getSerializableExtra("listin");
        String loc = getIntent().getStringExtra("location");
        setTitle(loc + " 15 Day");
        outLookList.addAll(temp);
        oAdapter.notifyItemRangeChanged(0, temp.size());
        super.onStart();
    }

    // Navigation
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Api Data
    // Refresh 2:10:40
}