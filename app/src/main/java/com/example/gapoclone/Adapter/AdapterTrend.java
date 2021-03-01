package com.example.gapoclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.Model.Trend;
import com.example.gapoclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTrend extends RecyclerView.Adapter<AdapterTrend.MyViewHolder> {

    private Context context;
    private ArrayList<Trend> trends;

    public AdapterTrend(Context context, ArrayList<Trend> trends) {
        this.context = context;
        this.trends = trends;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trend, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trend trend = trends.get(position);

        Picasso.get().load(trend.getUrlImageTrend())
                .into(holder.imgTrend);
        holder.tvOrder.setText(String.valueOf(position + 1));
        holder.tvTheme.setText(trend.getDescription());
    }

    @Override
    public int getItemCount() {
        return trends.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTrend;
        private TextView tvOrder;
        private TextView tvTheme;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTrend = itemView.findViewById(R.id.img_trends);
            tvOrder = itemView.findViewById(R.id.tv_order_trend);
            tvTheme = itemView.findViewById(R.id.tv_theme_trends);
        }
    }

    public void DeleteAll() {
        this.trends.clear();
        this.notifyDataSetChanged();
    }

    public void AddDataTrends(ArrayList<Trend> trends) {
        this.trends = trends;
    }
}
