package com.example.civiladvocacyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class OfficialActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;

    private TextView location;
    private TextView name;
    private TextView title;
    private TextView party;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView website;

    private TextView staticAddress;
    private TextView staticEmail;
    private TextView staticPhone;
    private TextView staticWebsite;

    private ImageView picture;
    private ImageView partyLogo;
    private ImageView twitter;
    private ImageView facebook;
    private ImageView youtube;

    private String locationSearched;
    private PoliticianObject politicianObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        constraintLayout = findViewById(R.id.officialConstaint);

        location = findViewById(R.id.officialLocation);

        name = findViewById(R.id.officialName);
        title = findViewById(R.id.officialTitle);
        party = findViewById(R.id.officialParty);
        address = findViewById(R.id.officialAddress);
        phone = findViewById(R.id.officialPhoneNumber);
        email = findViewById(R.id.officialEmail);
        website = findViewById(R.id.officialWebsite);

        staticAddress = findViewById(R.id.address);
        staticEmail = findViewById(R.id.email);
        staticPhone = findViewById(R.id.phone);
        staticWebsite = findViewById(R.id.website);

        picture = findViewById(R.id.officialPicture);
        partyLogo = findViewById(R.id.officialPartyImg);
        twitter = findViewById(R.id.officialTwitterImg);
        facebook = findViewById(R.id.officialFBImg);
        youtube = findViewById(R.id.officialYTImg);

        locationSearched = (String) getIntent().getSerializableExtra("LOCATION");
        politicianObject = (PoliticianObject) getIntent().getSerializableExtra("DATA");

        location.setText(locationSearched);

        email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        address.setPaintFlags(address.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        website.setPaintFlags(website.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        phone.setPaintFlags(phone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        partyLogo.setVisibility(View.INVISIBLE);
        if (politicianObject.getParty().equalsIgnoreCase("Republican Party")) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            partyLogo.setVisibility(View.VISIBLE);
            partyLogo.setImageResource(R.drawable.rep_logo);
        } else if (politicianObject.getParty().equalsIgnoreCase("Democratic Party")) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            partyLogo.setVisibility(View.VISIBLE);
            partyLogo.setImageResource(R.drawable.dem_logo);
        }

        name.setText(politicianObject.getName());
        title.setText(politicianObject.getTitle());
        party.setText("(" + politicianObject.getParty() + ")");


        // Contact Info
        if (politicianObject.getAddress().equals("")){
            address.setVisibility(View.INVISIBLE);
            staticAddress.setVisibility(View.INVISIBLE);
        } else {
            address.setText(politicianObject.getAddress());
            address.setVisibility(View.VISIBLE);
            staticAddress.setVisibility(View.VISIBLE);
        }

        if (politicianObject.getWebsite().equals("")){
            website.setVisibility(View.INVISIBLE);
            staticWebsite.setVisibility(View.INVISIBLE);
        } else {
            website.setText(politicianObject.getWebsite());
            website.setVisibility(View.VISIBLE);
            staticWebsite.setVisibility(View.VISIBLE);
        }

        if (politicianObject.getEmail().equals("")){
            email.setVisibility(View.INVISIBLE);
            staticEmail.setVisibility(View.INVISIBLE);
        } else {
            email.setText(politicianObject.getEmail());
            email.setVisibility(View.VISIBLE);
            staticEmail.setVisibility(View.VISIBLE);
        }

        if (politicianObject.getPhone().equals("")){
            phone.setVisibility(View.INVISIBLE);
            staticPhone.setVisibility(View.INVISIBLE);
        } else {
            phone.setText(politicianObject.getPhone());
            phone.setVisibility(View.VISIBLE);
            staticPhone.setVisibility(View.VISIBLE);
        }

        // Social media
        if (politicianObject.getTwitter().equals("")) {
            twitter.setVisibility(View.INVISIBLE);
        } else {
            twitter.setVisibility(View.VISIBLE);
        }

        if (politicianObject.getFacebook().equals("")) {
            facebook.setVisibility(View.INVISIBLE);
        } else {
            facebook.setVisibility(View.VISIBLE);
        }

        if (politicianObject.getYoutube().equals("")) {
            youtube.setVisibility(View.INVISIBLE);
        } else {
            youtube.setVisibility(View.VISIBLE);
        }

        String polPhoto = politicianObject.getPhoto();
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
                        }).into(picture);
            } catch (Exception e) {
                picture.setImageResource(R.drawable.brokenimage);
            }
        }

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficialActivity.this, PhotoDetailActivity.class);
                intent.putExtra("LOCATION", locationSearched);
                intent.putExtra("IMAGE", politicianObject.getPhoto());
                intent.putExtra("PARTY", politicianObject.getParty());
                intent.putExtra("NAME", politicianObject.getName());
                intent.putExtra("TITLE", politicianObject.getTitle());
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mainWay("com.google.android.youtube", "https://www.youtube.com/" + politicianObject.getYoutube());
                } catch (ActivityNotFoundException e) {
                    webBrowserWay("https://www.youtube.com/" + politicianObject.getYoutube());
                }
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mainWay("com.twitter.android", "twitter://user?screen_name=" + politicianObject.getTwitter());
                } catch (ActivityNotFoundException e) {
                    webBrowserWay("https://www.twitter.com/" + politicianObject.getTwitter());
                }
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mainWay("com.facebook.katana", "fb://facewebmodal/f?href=https://www.facebook.com/" + politicianObject.getFacebook());
                } catch (ActivityNotFoundException e) {
                    webBrowserWay("https://www.facebook.com/" + politicianObject.getFacebook());
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + politicianObject.getPhone()));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    toastWay("No valid phone app found");
                }
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(politicianObject.getAddress()));
                    Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    toastWay("No valid map app found");
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, politicianObject.getEmail());
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    toastWay("No valid email app found");
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webBrowserWay(politicianObject.getWebsite());
            }
        });
    }

    public void mainWay(String p, String d) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(p);
        intent.setData(Uri.parse(d));
        startActivity(intent);
    }

    public void toastWay(String s) {
        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void webBrowserWay(String s) {
        Intent intent = null;
        startActivity(new Intent(intent.ACTION_VIEW, Uri.parse(s)));
    }

    @Override
    public void onBackPressed() { super.onBackPressed(); }
}