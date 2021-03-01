package com.example.gapoclone.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterChooseImage extends RecyclerView.Adapter<AdapterChooseImage.MyViewHolder> {
    private Context context;
    private ArrayList<Uri> images;

    public AdapterChooseImage(Context context) {
        this.context = context;
    }

    public void setImages(ArrayList<Uri> images) {
        this.images = images;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_post_pattern_image, parent, false);
        return new MyViewHolder(view);
    }

    public void DeleteAll() {
        this.images.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Uri uri = images.get(position);
        Picasso.get().load(uri)
                .resize(100, 100)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (images != null)
            return images.size();
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img1);
        }
    }
}
