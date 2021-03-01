package com.example.gapoclone.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.Model.Person;
import com.example.gapoclone.Model.Post;
import com.example.gapoclone.R;
import com.example.gapoclone.Utilities.GridSpacingItemDecoration;
import com.example.gapoclone.Utilities.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyViewHolder> {


    private final Context context;
    private final ArrayList<Post> posts;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colPost;
    private CollectionReference colPerson;

    public AdapterPost(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
        colPost = db.collection("Post");
        colPerson = db.collection("Person");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = posts.get(position);


        Picasso.get().load(PersonAPI.getInstance().getPersonImg()).
                resize(80, 80).
                error(R.drawable.ic_baseline_error_24).into(holder.imgComment);
        holder.tvTitlePost.setText(post.getContent().getContentText());
        holder.tvTimeAgoPost.setText(Tools.convertTimeAgo(post.getPostAgo()));
        if (post.getComments() != null)
            holder.tvAmountComment.setText(post.getComments().size() + " Bình luận");
        else
            holder.tvAmountComment.setText(0 + " Bình luận");

        if (post.getReacts() != null)
            holder.tvAmountLike.setText(String.valueOf(post.getReacts().size()));
        else
            holder.tvAmountLike.setText(String.valueOf(0));
        //find person
        colPerson.document(post.getIdPerson())
                .get().addOnCompleteListener(task -> {
            Person person = Objects.requireNonNull(task.getResult()).toObject(Person.class);
            if (person != null) {
                holder.tvPersonPost.setText(person.getName());
                Picasso.get().load(person.getPersonImg())
                        .into(holder.imgPersonPost);
            }
        });

        AdapterPostImage adapterPostImage = new AdapterPostImage(post.getImgPost());

        //if post have image
        if (post.getImgPost() != null && post.getImgPost().size() != 0) {
            //if post have even image and small than six picture to handle
            if (post.getImgPost().size() % 2 == 0 && post.getImgPost().size() < 6) {
                holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                holder.recyclerView.addItemDecoration(new GridSpacingItemDecoration(
                        2, 1, false, 0));
            }//if post dont have even image or large than six picture
            else
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

            holder.recyclerView.setAdapter(adapterPostImage);
            holder.recyclerView.setVisibility(View.VISIBLE);
        } //if dont have image but have background
        else if (post.getContent().getBackgroundPost() != android.R.drawable.screen_background_light_transparent) {
            holder.tvTitlePost.getLayoutParams().height = 600;
            holder.tvTitlePost.setBackgroundResource(post.getContent().getBackgroundPost());
            holder.tvTitlePost.setGravity(Gravity.CENTER);

            holder.tvTitlePost.setTextSize(25f);
            holder.recyclerView.setVisibility(View.GONE);
        }//if dont have background
        else {
            holder.tvTitlePost.setPadding(16, 0, 16, 0);
            holder.recyclerView.setVisibility(View.GONE);
        }


        //set image type post user choose
        switch (post.getContent().getTypePost()) {
            case "Công khai":
                holder.imgTypePost.setImageResource(R.drawable.ic_baseline_public_24);
                break;
            case "Bạn bè":
                holder.imgTypePost.setImageResource(R.drawable.ic_baseline_groups_24);
                break;
            default:
                holder.imgTypePost.setImageResource(R.drawable.ic_baseline_person_24);
                break;
        }


        holder.btnLike
                .setOnClickListener(v -> {
                    //https://stackoverflow.com/questions/46714018/change-android-button-drawable-icon-color-programmatically
                    ColorStateList colorStateListGreen = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green));
                    ColorStateList colorStateListGray = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray));
                    if (holder.btnLike.getCompoundDrawableTintList() != colorStateListGreen) {
                        holder.btnLike.setCompoundDrawableTintList(colorStateListGreen);
                        //https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically
                        holder.btnLike.setTextColor(ContextCompat.getColor(context, R.color.green));
                        holder.tvAmountLike.setText(
                                String.valueOf(Integer.parseInt(holder.tvAmountLike.getText().toString()) + 1)
                        );
                    } else {
                        holder.btnLike.setCompoundDrawableTintList(colorStateListGray);
                        holder.btnLike.setTextColor(ContextCompat.getColor(context, R.color.black));
                        holder.tvAmountLike.setText(
                                String.valueOf(Integer.parseInt(holder.tvAmountLike.getText().toString()) - 1));
                    }


                });

        holder.btnLike.setOnLongClickListener(v -> {
            Toast.makeText(context, holder.tvTitlePost.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
        });

    }

    public void DeleteAll() {
        this.posts.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recyclerView;
        private ImageView imgComment, imgTypePost, imgPersonPost;
        private final TextView tvPersonPost, tvAmountComment, tvAmountLike, tvTitlePost, tvTimeAgoPost;
        private Button btnLike;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPersonPost = itemView.findViewById(R.id.img_person_post);
            imgComment = itemView.findViewById(R.id.img_person_comment);
            imgTypePost = itemView.findViewById(R.id.img_type_post);
            recyclerView = itemView.findViewById(R.id.recycler_image_post);
            tvPersonPost = itemView.findViewById(R.id.tv_name_person_post);
            tvTimeAgoPost = itemView.findViewById(R.id.tv_time_ago_post);
            tvTitlePost = itemView.findViewById(R.id.tv_title_post);
            tvAmountLike = itemView.findViewById(R.id.tv_amount_like);
            tvAmountComment = itemView.findViewById(R.id.tv_amount_comment);
            btnLike = itemView.findViewById(R.id.btn_like);
        }
    }
}
