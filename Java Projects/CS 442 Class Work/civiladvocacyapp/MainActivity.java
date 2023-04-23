package com.example.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // DOWNLOAD IMAGE 10-11-2022 45 mins

    // Elements in MainActivity
    private TextView addressBar;
    private RecyclerView recyclerView;

    // Recycler View
    private final ArrayList<PoliticianObject> politicianList = new ArrayList<>();
    private PoliticianAdapter pAdapter;

    // Location definitions
    public String locationString;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST = 111;
    private boolean permissionGiven = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Civil Advocacy");
        addressBar = findViewById(R.id.mainTextLocation);
        recyclerView = findViewById(R.id.mainRecyclerView);
        pAdapter = new PoliticianAdapter(politicianList, this);
        recyclerView.setAdapter(pAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        determineLocation();
        // Make sure gps is enabled try maps first
    }

    @Override
    protected void onStart() {
        addressBar.setText("No Data For Location");
        // Flip the colors
        // Set Message
        if (hasNetworkConnection() != false && permissionGiven == true){
            addressBar.setText(locationString);
            DownloadVolley.getData(this, locationString);
        }
        super.onStart();
    }

    private void determineLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                locationString = getPlace(location);
                permissionGiven = true;
                onStart();
            }
        }).addOnFailureListener(this, e ->
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private String getPlace(Location loc) {
        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            sb.append(String.format(Locale.getDefault(), "%s, %s", city, state));

        } catch (IOException e) {}
        return sb.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGiven = true;
                    determineLocation();
                } else {
                    addressBar.setText("Location Permission Denied");
                }
            }
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (hasNetworkConnection() == true) {
            if (item.getItemId() == R.id.main_menu_about) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.main_menu_location) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Enter Address");
                final EditText loc = new EditText(this);
                loc.setGravity(Gravity.CENTER_HORIZONTAL);
                loc.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(loc);

                builder.setNegativeButton("CANCEL", (dialog, id) -> {});
                builder.setPositiveButton("OK", (dialog, id) -> {
                    locationString = loc.getText().toString();
                    addressBar.setText(locationString);
                    try {
                        DownloadVolley.getData(this, locationString);
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Not a valid location", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {}
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Clicking item
    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        PoliticianObject p = politicianList.get(pos);

        Intent intent = new Intent(this, OfficialActivity.class);
        intent.putExtra("LOCATION", locationString);
        intent.putExtra("DATA", p);
        startActivity(intent);
    }

    public void updateData(ArrayList<PoliticianObject> pl) {
        politicianList.clear();
        politicianList.addAll(pl);
        pAdapter.notifyItemRangeChanged(0, politicianList.size());
    }
}