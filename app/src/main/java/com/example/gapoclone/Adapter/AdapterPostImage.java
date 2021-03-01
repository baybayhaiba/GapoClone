package com.example.gapoclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterPostImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> imgPost;
    public static final int PHOTO_PATTERN = 1;
    public static final int PHOTO_THREE_IMAGE = 2;
    public static final int PHOTO_MORE_IMAGE = 3;

    public AdapterPostImage(ArrayList<String> imgPost) {
        this.imgPost = imgPost;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
        }
    }

    public static class PhotoThreeViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1, img2, img3;

        public PhotoThreeViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
        }
    }


    public static class PhotoMoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1, img2, img3, img4, img5;
        private TextView tvImage;

        public PhotoMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (PHOTO_PATTERN == viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post_pattern_image, parent, false);
            return new PhotoViewHolder(view);
        } else if (PHOTO_THREE_IMAGE == viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post_three_image, parent, false);
            return new PhotoThreeViewHolder(view);
        } else if (PHOTO_MORE_IMAGE == viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post_more_image, parent, false);
            return new PhotoMoreViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == -1)
            return;
        String url = imgPost.get(position);
        if (PHOTO_PATTERN == this.getItemViewType(position)) {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            Picasso.get().load(url)
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoViewHolder.img1);
        } else if (PHOTO_THREE_IMAGE == this.getItemViewType(position)) {
            PhotoThreeViewHolder photoThreeViewHolder = (PhotoThreeViewHolder) holder;
            Picasso.get().load(imgPost.get(0))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoThreeViewHolder.img1);
            Picasso.get().load(imgPost.get(1))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoThreeViewHolder.img2);
            Picasso.get().load(imgPost.get(2))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoThreeViewHolder.img3);

        } else if (PHOTO_MORE_IMAGE == this.getItemViewType(position)) {
            PhotoMoreViewHolder photoMoreViewHolder = (PhotoMoreViewHolder) holder;
            Picasso.get().load(imgPost.get(0))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoMoreViewHolder.img1);
            Picasso.get().load(imgPost.get(1))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoMoreViewHolder.img2);
            Picasso.get().load(imgPost.get(2))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoMoreViewHolder.img3);
            Picasso.get().load(imgPost.get(3))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoMoreViewHolder.img4);
            Picasso.get().load(imgPost.get(4))
                    .placeholder(R.drawable.loading).resize(200, 200).into(photoMoreViewHolder.img5);

        }
    }

    @Override
    public int getItemCount() {
        if (imgPost == null)
            return 0;
        else if (imgPost.size() >= 5 || imgPost.size() == 3)
            return 1;
        else return imgPost.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (imgPost.size() >= 5)
            return PHOTO_MORE_IMAGE;
        else if (imgPost.size() == 1 || imgPost.size() % 2 == 0)
            return PHOTO_PATTERN;
        else {
            return PHOTO_THREE_IMAGE;
        }
    }
}
