package com.example.newaggregator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private final MainActivity mainActivity;
    private final ArrayList<ArticleObject> articleList;

    public ArticleAdapter (MainActivity mainActivity, ArrayList<ArticleObject> articleList) {
        this.mainActivity = mainActivity;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleObject articleObject = articleList.get(position);

        holder.title.setVisibility(View.INVISIBLE);
        if (!(articleObject.getTitle().equals(""))) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(articleObject.getTitle());

            if (!(articleObject.getUrl().equals(""))) {
                holder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mainActivity.openBrowser(articleObject.getUrl());
                        } catch (Exception e) {}
                    }
                });
            }
        }

        holder.date.setVisibility(View.INVISIBLE);
        if (!(articleObject.getPublished().equals(""))) {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(articleObject.getPublished());
        }

        holder.author.setVisibility(View.INVISIBLE);
        if (!(articleObject.getPublished().equals(""))) {
            holder.author.setVisibility(View.VISIBLE);
            holder.author.setText(articleObject.getAuthor());
        }

        holder.body.setVisibility(View.INVISIBLE);
        if (!(articleObject.getPublished().equals(""))) {
            holder.body.setVisibility(View.VISIBLE);
            holder.body.setText(articleObject.getDescription());

            if (!(articleObject.getUrl().equals(""))) {
                holder.body.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mainActivity.openBrowser(articleObject.getUrl());
                        } catch (Exception e) {}
                    }
                });
            }
        }

        holder.number.setVisibility(View.INVISIBLE);
        if (!(articleObject.getPublished().equals(""))) {
            holder.number.setVisibility(View.VISIBLE);
            holder.number.setText((position + 1) + " of " + articleList.size());
        }

        if (!(articleObject.getImage().equals("") || articleObject.getImage().equalsIgnoreCase("null"))) {
            try {
                Glide.with(mainActivity)
                        .load(articleObject.getImage())
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).dontAnimate().into(holder.image);

            } catch (Exception e) {
                holder.image.setImageResource(R.drawable.brokenimage);
            }
        }

        if (!(articleObject.getUrl().equals(""))) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mainActivity.openBrowser(articleObject.getUrl());
                    } catch (Exception e) {}
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
