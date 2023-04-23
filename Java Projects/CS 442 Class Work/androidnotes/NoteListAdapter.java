package com.example.androidnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static List<Note> noteList;
    private final MainActivity mainAct;

    NoteListAdapter(List<Note> nl, MainActivity ma) {
        this.noteList = nl;
        mainAct = ma;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_entry, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct); // Might not need this

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);

        String labelLimit = note.getLabel();
        if (note.getLabel().length() > 80) {
            labelLimit = note.getLabel().substring(0, 80) + " ...";
        }

        String descLimit = note.getDescription();
        if (note.getDescription().length() > 80){
            descLimit = note.getDescription().substring(0, 80) + " ...";
        }

        holder.label.setText(labelLimit);
        holder.date.setText(note.getDate());
        holder.description.setText(descLimit);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
