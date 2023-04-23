package com.example.newaggregator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBindings;

import com.example.newaggregator.databinding.ArticleItemBinding;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView date;
    TextView author;
    TextView body;
    TextView number;
    ImageView image;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.article_title);
        date = itemView.findViewById(R.id.article_date);
        author = itemView.findViewById(R.id.article_author);
        body = itemView.findViewById(R.id.article_main);
        number = itemView.findViewById(R.id.article_count);
        image = itemView.findViewById(R.id.imageView);
    }
}
