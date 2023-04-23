package com.example.androidnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final ArrayList<Note> myNotes = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private RecyclerView recyclerView;
    private NoteListAdapter nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For passing data between different activities
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::noteAddedChanged);

        recyclerView = findViewById(R.id.recycler);
        nAdapter = new NoteListAdapter(myNotes, this);
        recyclerView.setAdapter(nAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFile();
    }

    // Process for sending and collecting data from different activities
    public void addNote(View v){
        Intent intent = new Intent(this, EditActivity.class);
        activityResultLauncher.launch(intent);
    }

    // What happens when data comes from Edit Activity
    public void noteAddedChanged(ActivityResult activityResult){
        if (activityResult.getResultCode() == RESULT_OK) {
            Intent data = activityResult.getData();
            if (data == null)
                return;
            if (data.hasExtra("NEW_NOTE")) {
                Note newNote = (Note) data.getSerializableExtra("NEW_NOTE");
                myNotes.add(0, newNote);
                nAdapter.notifyItemInserted(0);
            } else if (data.hasExtra("EDITED_NOTE")){
                Note editNote = (Note) data.getSerializableExtra("EDITED_NOTE");
                int pos = data.getIntExtra("EDITED_POS", 0);
                myNotes.remove(pos);
                nAdapter.notifyItemRemoved(pos);
                myNotes.add(0, editNote);
                nAdapter.notifyItemInserted(0);
            }
            saveNotes();
        }
    }

    // Create menu
    @Override
    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menu options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_info) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menu_add) {
            addNote(null);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Clicking note
    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Note n = myNotes.get(pos);

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("EDIT_NOTE", n);
        intent.putExtra("EDIT_POS", pos);
        activityResultLauncher.launch(intent);

    }

    // Deleting note
    @Override
    public boolean onLongClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        String titleLimited = myNotes.get(pos).getLabel();
        if (titleLimited.length() > 80){
            titleLimited = myNotes.get(pos).getLabel().substring(0, 80) + " ...";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myNotes.remove(pos);
                nAdapter.notifyItemRemoved(pos);
                saveNotes();
            }
        });
        builder.setNegativeButton("NO", ((dialogInterface, i) -> {}));
        builder.setMessage("Are you sure you want to delete '" + titleLimited +"'?");
        builder.setTitle("Delete");

        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    // Loading file
    private void loadFile(){
        // Might have to modify to account for multiple notes
        try {
            InputStream is = getApplicationContext().openFileInput("NoteItem.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Parse the Array
            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String label = jsonObject.getString("label");
                String date = jsonObject.getString("date");
                String description = jsonObject.getString("description");
                myNotes.add(new Note(label, date, description)); // Include time; May edit file
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Saving file
    private void saveNotes() {
        String output = toJSON(myNotes);

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("NoteItem.json", Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(output);
            printWriter.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Saving to jason specifically
    @NonNull
    public String toJSON(ArrayList<Note> myNotes) {
        // Might have to modify so it's an array with in an array
        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");
            jsonWriter.beginArray();

            for (Note n : myNotes) {
                jsonWriter.beginObject();
                jsonWriter.name("label").value(n.getLabel());
                jsonWriter.name("date").value(n.getDate());
                jsonWriter.name("description").value(n.getDescription());
                jsonWriter.endObject();
            }

            jsonWriter.endArray();
            jsonWriter.close();
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}