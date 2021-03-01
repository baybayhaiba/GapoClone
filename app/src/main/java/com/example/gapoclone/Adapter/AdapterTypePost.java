package com.example.gapoclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.Model.TypePost;
import com.example.gapoclone.R;

import java.util.ArrayList;

public class AdapterTypePost extends RecyclerView.Adapter<AdapterTypePost.MyViewHolder> {

    private Context context;
    private ArrayList<TypePost> typePosts;

    private TypePostOnClick listener;

    public interface TypePostOnClick {
        void onClick(TypePost typePost);
    }

    public AdapterTypePost(Context context, ArrayList<TypePost> typePosts) {
        this.context = context;
        this.typePosts = typePosts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_background_type_post, parent, false);
        return new MyViewHolder(view);
    }

    public void setListener(TypePostOnClick listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TypePost typePost = typePosts.get(position);
        holder.imgTypePost.setImageResource(typePost.getDrawable_post());
        holder.cardView.setOnClickListener(v -> { listener.onClick(typePost); });
    }

    @Override
    public int getItemCount() {
        return typePosts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imgTypePost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.custom_cv_type_post);
            imgTypePost = itemView.findViewById(R.id.custom_img_type_post);
        }
    }
}
