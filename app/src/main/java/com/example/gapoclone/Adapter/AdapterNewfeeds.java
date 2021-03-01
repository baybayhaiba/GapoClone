package com.example.gapoclone.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.Model.NewsFeed;
import com.example.gapoclone.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNewfeeds extends RecyclerView.Adapter<AdapterNewfeeds.MyViewHolder> {

    private Context context;
    private ArrayList<NewsFeed> newsFeeds;

    public AdapterNewfeeds(Context context, ArrayList<NewsFeed> newsFeeds) {
        this.context = context;
        this.newsFeeds = newsFeeds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_newfeeds, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsFeed newsFeed = newsFeeds.get(position);

        if (TextUtils.isEmpty(newsFeed.getName_owner())) {
            holder.addNewsFeed.setVisibility(View.VISIBLE);
            holder.imgOwner.setVisibility(View.GONE);
            holder.tvNameOwner.setText("Thêm vào bản tin");
        } else {
            holder.imgOwner.setVisibility(View.VISIBLE);
            holder.addNewsFeed.setVisibility(View.GONE);
            holder.tvNameOwner.setText(newsFeed.getName_owner());
        }


        Picasso.get().load(newsFeed.getImage_owner()).placeholder(R.drawable.user)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.imgOwner);
        Picasso.get().load(newsFeed.getImage_news_feed())
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.imgNewsFeed);

        holder.cvNewsFeed.setOnClickListener(v -> Toast.makeText(context, newsFeed.getName_owner(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return newsFeeds.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgOwner;
        private CardView cvNewsFeed;
        private ImageView imgNewsFeed;
        private FloatingActionButton addNewsFeed;
        private TextView tvNameOwner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOwner = itemView.findViewById(R.id.img_owner);
            cvNewsFeed = itemView.findViewById(R.id.cvNewsFeed);
            tvNameOwner = itemView.findViewById(R.id.tv_name_newfeeds);
            addNewsFeed = itemView.findViewById(R.id.floatting_newsfeed);
            imgNewsFeed = itemView.findViewById(R.id.img_newsfeed);
        }
    }

    public void DeleteAll() {
        this.newsFeeds.clear();
        this.notifyDataSetChanged();
    }

    public void AddDataNewsFeeds(ArrayList<NewsFeed> newsFeeds) {
        this.newsFeeds = newsFeeds;
    }
}
