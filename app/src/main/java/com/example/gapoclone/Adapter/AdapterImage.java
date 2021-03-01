package com.example.gapoclone.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.MyViewHolder> {

    private Context context;
    private ArrayList<Uri> uris;

    public AdapterImage(Context context, ArrayList<Uri> uris) {
        this.context = context;
        this.uris = uris;
    }

    public interface ListenerImage {
        void onClick(Uri uri, boolean isChecked);
    }

    private ListenerImage listenerImage;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Uri uri = uris.get(position);
        holder.radioButton.setChecked(false);
        Picasso.get().load(uri)
                .resize(400, 400)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageView.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.cardView.setOnClickListener(v -> {
            holder.radioButton.setChecked(!holder.radioButton.isChecked());
            listenerImage.onClick(uri, holder.radioButton.isChecked());
        });
    }

    public void setListenerImage(ListenerImage listenerImage) {
        this.listenerImage = listenerImage;
    }

    @Override
    public int getItemCount() {
        if (uris != null)
            return uris.size();
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ImageView imageView;
        CardView cardView;
        RadioButton radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.cardView);
            radioButton = itemView.findViewById(R.id.radChooseImage);
        }
    }
}
