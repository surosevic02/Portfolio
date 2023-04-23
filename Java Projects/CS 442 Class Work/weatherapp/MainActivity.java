package com.example.weatherapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Application parameters and variables
    public String location = "Chicago, IL";
    public String unit = "us";
    public String speedUnit = " mph";
    public String distanceUnit = " mi";
    public String unitText = "°F";

    // Options menu
    private Menu m;

    // Swiper
    private SwipeRefreshLayout swiperLayout;

    // Recycler
    private final List<Hourly> hourlyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HourlyAdapter hAdapter;

    // Outlook list
    //private ActivityResultLauncher<Intent> activityResultLauncher;

    private final List<OutLook> outLookList = new ArrayList<>();

    TextView date;
    TextView temp;
    TextView feelsLike;
    TextView humidity;
    TextView wind;
    TextView visibility;
    TextView desc;
    TextView uvindex;
    TextView sunrise;
    TextView sunset;
    TextView morning;
    TextView afternoon;
    TextView evening;
    TextView night;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Home screen text fields and Icon
        date = findViewById(R.id.text_date_ma);
        temp = findViewById(R.id.text_temp);
        feelsLike = findViewById(R.id.text_feels);
        humidity = findViewById(R.id.text_humid);
        wind = findViewById(R.id.text_wind);
        visibility = findViewById(R.id.text_visibility);
        desc = findViewById(R.id.text_desc_ma);
        uvindex = findViewById(R.id.text_uv_ma);
        sunrise = findViewById(R.id.text_sunrise);
        sunset = findViewById(R.id.text_sunset);
        morning = findViewById(R.id.text_morn_ma);
        afternoon = findViewById(R.id.text_after_ma);
        evening = findViewById(R.id.text_even_ma);
        night = findViewById(R.id.text_night_ma);
        icon = findViewById(R.id.imageViewMa);

        // Recycler
        recyclerView = findViewById(R.id.hourlyRecycler);
        hAdapter = new HourlyAdapter(hourlyList, this);
        recyclerView.setAdapter(hAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Swiper
        swiperLayout = findViewById(R.id.swiper);

        swiperLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String msg = "Refreshing...";
                if (hasNetworkConnection() != false) {
                    LoaderVolley.getSourceData(MainActivity.this, location, unit);
                } else {
                    date.setText("No internet connection");
                    msg = "Unable to Connect";
                }
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();
                swiperLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStart() {
        date.setText("No internet connection");
        if (hasNetworkConnection() != false) {
            LoaderVolley.getSourceData(this, location, unit); // Maybe add specification for celsius or far
        }
        super.onStart();
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    // Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        m = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // ADD TOAST MESSAGE WHEN NO NET
        if (hasNetworkConnection() == true) {
            if (item.getItemId() == R.id.mm_toggle) {
                // Icon change to current measurement
                if (unit == "us") {
                    unit = "metric";
                    speedUnit = " mps";
                    distanceUnit = " km";
                    unitText = "°C";
                    m.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.units_c));
                } else {
                    unit = "us";
                    speedUnit = " mph";
                    distanceUnit = " mi";
                    unitText = "°F";
                    m.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.units_f));
                }
                LoaderVolley.getSourceData(this, location, unit);
                return true;
            } else if (item.getItemId() == R.id.mm_outlook) {
                // Change to do similar process as addNote();
                Intent intent = new Intent(MainActivity.this, DailyForecastActivity.class);
                intent.putExtra("listin", (Serializable) outLookList);
                intent.putExtra("location", location);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.mm_location) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                //builder.setIcon(R.drawble.ICONNAMEHERE); -- Change to given Icon location
                builder.setTitle("Enter a Location");
                builder.setMessage("For US loctions, enter as 'City', or 'City, State'" + "\n\n" + "For International locations enter as 'City, Country'");

                final EditText loc = new EditText(this);
                loc.setInputType(InputType.TYPE_CLASS_TEXT);
                loc.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(loc);

                builder.setPositiveButton("OK", (dialog, id) -> {
                    location = loc.getText().toString();
                    LoaderVolley.getSourceData(this, location, unit);
                });
                builder.setNegativeButton("CANCEL", (dialog, id) -> {
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
            toast.show();
            return super.onOptionsItemSelected(item);
        }
    }

    public void downloadFailed() {
        outLookList.clear();
        hourlyList.clear();
        hAdapter.notifyItemChanged(0, hourlyList.size());
    }

    public void updateData(ArrayList<String> currentList, ArrayList<Hourly> hList, ArrayList<OutLook> oList) {
        // Setting the Home Screen
        setTitle(location);
        date.setText(currentList.get(0));
        temp.setText(currentList.get(1) + unitText);
        feelsLike.setText("Feels Like " + currentList.get(2) + unitText);
        humidity.setText("Humidity: " + currentList.get(3) +"%");
        wind.setText("Winds: " + currentList.get(6) + " at " + currentList.get(5) + speedUnit + " gusting to " + currentList.get(4) + speedUnit);
        desc.setText(currentList.get(10) + " (" + currentList.get(8) + "% clouds)");
        uvindex.setText("UV Index: " + currentList.get(9));
        visibility.setText("Visibility: " + currentList.get(7) + distanceUnit);
        sunrise.setText("Sunrise: " + currentList.get(12));
        sunset.setText("Sunset: " + currentList.get(13));
        morning.setText(currentList.get(14) + unitText);
        afternoon.setText(currentList.get(15) + unitText);
        evening.setText(currentList.get(16) + unitText);
        night.setText(currentList.get(17) + unitText);
        try {
            icon.setImageResource(this.getResources().getIdentifier(currentList.get(18), "drawable", this.getPackageName()));
        } catch (Exception e) {}

        // Setting the Recycler
        hourlyList.clear();
        hourlyList.addAll(hList);
        //hAdapter.notifyItemChanged(0, hList.size());
        hAdapter.notifyItemRangeChanged(0, hList.size());
        //hAdapter.notifyDataSetChanged(0, hList.size());

        // Setting the OutLookList
        outLookList.clear();
        outLookList.addAll(oList);
    }
}
