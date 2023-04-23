package com.example.androidnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.edit_title_text);
        description = findViewById(R.id.edit_description_text);

        Intent intent = getIntent();
        if (intent.hasExtra("EDIT_NOTE")){
            Note n = (Note) intent.getSerializableExtra("EDIT_NOTE");
            title.setText(n.getLabel());
            description.setText(n.getDescription());
        }
    }

    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Save button in menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_edit_menu){
            if (title.getText().toString().isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", ((dialogInterface, i) -> {}));
                builder.setMessage("Notes without a title will not be saved.");
                builder.setTitle("No Title");

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            } else {
                saveNote(null);
                return true;
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    // Process for pressing back
    @Override
    public void onBackPressed() {
        // If no string in title
        if (title.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", (((dialogInterface, i) -> {super.onBackPressed();})));
            builder.setNegativeButton("Cancel", ((dialogInterface, i) -> {}));
            builder.setTitle("No Title");
            builder.setMessage("Notes without a title will not be saved.");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        // String in title
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    saveNote(null);
                }
            });
            builder.setNegativeButton("No", ((dialogInterface, i) -> {super.onBackPressed();}));
            builder.setTitle("Unsaved Changes");
            builder.setMessage("Do you want to save any changes?");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // Save note
    public void saveNote(View v) {
        String titleText = title.getText().toString();
        String descriptionText = description.getText().toString();
        String date = new Date().toString();

        Note n = new Note (titleText, date, descriptionText);

        String key = "NEW_NOTE";

        // Return note number to be deleted
        Intent intent = getIntent();
        if (getIntent().hasExtra("EDIT_NOTE")){
            key = "EDITED_NOTE";

        }
        Intent returnVal = new Intent();
        returnVal.putExtra(key, n);
        if (intent.hasExtra("EDIT_POS")){
            int pos = intent.getIntExtra("EDIT_POS", 0);
            returnVal.putExtra("EDITED_POS", pos);
        }
        setResult(RESULT_OK, returnVal);
        finish();
    }
}