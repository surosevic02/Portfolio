package com.example.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("Civil Advocacy");
        textView = findViewById(R.id.textView5);

        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                startActivity(new Intent(intent.ACTION_VIEW, Uri.parse("https://developers.google.com/civic-information/")));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}