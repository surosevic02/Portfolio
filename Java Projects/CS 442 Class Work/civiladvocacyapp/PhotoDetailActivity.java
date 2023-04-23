package com.example.civiladvocacyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class PhotoDetailActivity extends AppCompatActivity {

    private TextView locationSearched;
    private TextView name;
    private TextView title;

    private ImageView photo;
    private ImageView logo;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        locationSearched = findViewById(R.id.photoDetLocation);
        name = findViewById(R.id.photoDetName);
        title = findViewById(R.id.photoDetTitle);

        photo = findViewById(R.id.photoDetImage);
        logo = findViewById(R.id.photoDetParty);

        constraintLayout = findViewById(R.id.photoDetConstraint);

        String location = (String) getIntent().getSerializableExtra("LOCATION");
        String nameGiven = (String) getIntent().getSerializableExtra("NAME");
        String titleGiven = (String) getIntent().getSerializableExtra("TITLE");

        locationSearched.setText(location);
        name.setText(nameGiven);
        title.setText(titleGiven);

        String partyGiven = (String) getIntent().getSerializableExtra("PARTY");
        logo.setVisibility(View.INVISIBLE);
        if (partyGiven.equalsIgnoreCase("Republican Party")) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            logo.setVisibility(View.VISIBLE);
            logo.setImageResource(R.drawable.rep_logo);
        } else if (partyGiven.equalsIgnoreCase("Democratic Party")) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            logo.setVisibility(View.VISIBLE);
            logo.setImageResource(R.drawable.dem_logo);
        }

        String polPhoto = (String) getIntent().getSerializableExtra("IMAGE");
        // photo.setImageResource(R.drawable.missing);
        if (!(polPhoto.equals(""))) {
            try {
                Glide.with(this)
                        .load(polPhoto)
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(photo);
            } catch (Exception e) {
                photo.setImageResource(R.drawable.brokenimage);
            }
        }
        ImageView iv = findViewById(R.id.photoDetImage);
    }

    @Override
    public void onBackPressed() { super.onBackPressed(); }
}