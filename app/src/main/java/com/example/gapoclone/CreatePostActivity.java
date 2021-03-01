package com.example.gapoclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gapoclone.Adapter.AdapterChooseImage;
import com.example.gapoclone.Adapter.AdapterTypePost;
import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.Main.MainActivity;
import com.example.gapoclone.Model.Comment;
import com.example.gapoclone.Model.Content;
import com.example.gapoclone.Model.Post;
import com.example.gapoclone.Model.React;
import com.example.gapoclone.Model.TypePost;
import com.example.gapoclone.Utilities.GridSpacingItemDecoration;
import com.example.gapoclone.Utilities.Tools;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.Timestamp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreatePostActivity extends AppCompatActivity {


    private MaterialToolbar toolbar;
    private ImageView imgTypePost, statusTypePost, imgChooseImagePost;
    private TextView tvTypePost;
    private CardView cvChooseTypePost;
    private AdapterTypePost adapterTypePost;
    private ArrayList<TypePost> typePosts;
    private RecyclerView recyclerViewTypePost, recyclerViewChooseImage;
    private EditText edPost;
    private LinearLayout linearShowTypePost;
    private ViewGroup.MarginLayoutParams marginParams;
    private int type_post = Tools.BACKGROUND_DEFAULT;

    public static final int REQUEST_IMAGE = 1;
    private AdapterChooseImage adapterChooseImage;
    private StorageReference storageRef;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Uri> uris;

    private interface ListenerHandleImage {
        void url_image(ArrayList<String> urls);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        InitUI();
        checkTypePost();
        SendPost();
        TestChooseMultipleImage();
    }

    private void InitUI() {
        toolbar = findViewById(R.id.toolbar_create_post);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        imgChooseImagePost = findViewById(R.id.img_choose_image_post);
        cvChooseTypePost = findViewById(R.id.cv_type_post);
        cvChooseTypePost.setOnClickListener(this::showPopup);
        imgTypePost = findViewById(R.id.img_type_post);
        tvTypePost = findViewById(R.id.tv_type_post);
        statusTypePost = findViewById(R.id.img_status_type_post);
        firebaseDatabase = FirebaseDatabase.getInstance();
        edPost = findViewById(R.id.ed_post);
        linearShowTypePost = findViewById(R.id.linearShowTypePost);
        typePosts = initData();
        adapterTypePost = new AdapterTypePost(this, typePosts);
        recyclerViewTypePost = findViewById(R.id.recyclerViewTypePost);
        recyclerViewTypePost.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewTypePost.setAdapter(adapterTypePost);
        marginParams = (ViewGroup.MarginLayoutParams) edPost.getLayoutParams();
        adapterChooseImage = new AdapterChooseImage(this);
        statusTypePost.setOnClickListener(v -> {
            handleStatusTypePost();
        });
        adapterTypePost.setListener(this::handleBackgroundTypePost);
        recyclerViewChooseImage = findViewById(R.id.recyclerview_choose_image);
        recyclerViewChooseImage.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewChooseImage.setAdapter(adapterChooseImage);
        recyclerViewChooseImage.addItemDecoration(new GridSpacingItemDecoration(4,
                5, false, 0));
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    private void handleBackgroundTypePost(TypePost typePost) {
        edPost.setBackgroundResource(typePost.getDrawable_post());
        this.type_post = typePost.getDrawable_post();
        if (typePost.getDrawable_post() != Tools.BACKGROUND_DEFAULT) {
            //set gravity
            edPost.setGravity(Gravity.CENTER);
            //set margin edittext
            marginParams.setMargins(0, 0, 0, 0);
        } else {
            edPost.setGravity(Gravity.TOP | Gravity.START);
            marginParams.setMargins(40, 0, 40, 0);
        }
    }

    private void checkTypePost() {
        edPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if it so long than limit text
                if ((s.length() > 100
                        && linearShowTypePost.getVisibility() == View.VISIBLE)
                        || adapterChooseImage.getItemCount() != 0) {
                    edPost.setBackgroundResource(typePosts.get(0).getDrawable_post());
                    type_post = typePosts.get(0).getDrawable_post();
                    linearShowTypePost.setVisibility(View.GONE);
                    edPost.setGravity(Gravity.TOP | Gravity.START);
                } else {
                    linearShowTypePost.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private ArrayList<TypePost> initData() {
        ArrayList<TypePost> typePosts = new ArrayList<>();
        typePosts.add(new TypePost("0", Tools.BACKGROUND_DEFAULT));
        typePosts.add(new TypePost("1", R.drawable.type_post1));
        typePosts.add(new TypePost("2", R.drawable.type_post2));
        typePosts.add(new TypePost("3", R.drawable.type_post3));
        typePosts.add(new TypePost("4", R.drawable.type_post4));

        return typePosts;
    }

    private void handleStatusTypePost() {
        if (statusTypePost.getContentDescription().equals("collapse")) {
            statusTypePost.setImageResource(R.drawable.ic_baseline_add_circle_24);
            statusTypePost.setContentDescription("expand");
            typePosts.clear();
        } else {
            statusTypePost.setImageResource(R.drawable.ic_baseline_remove_circle_24);
            statusTypePost.setContentDescription("collapse");
            typePosts.addAll(initData());
        }
        adapterTypePost.notifyDataSetChanged();
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.menu_type_post, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnPublic:
                    setTypePost("Công khai", R.drawable.ic_baseline_public_24);
                    return true;
                case R.id.mnFriend:
                    setTypePost("Bạn bè", R.drawable.ic_baseline_groups_24);
                    return true;
                case R.id.mnAlone:
                    setTypePost("Chi một mình", R.drawable.ic_baseline_person_24);
                    return true;
                default:
                    return false;
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void setTypePost(String text, int type_post) {
        tvTypePost.setText(text);
        imgTypePost.setImageResource(type_post);
    }

    private void UploadPostToFirebase() {
//        (String idPost, String idPerson, ArrayList<String> imgPost,
//                ArrayList<Comment> comments, ArrayList<React> reacts,
//                Content content, Timestamp postAgo)
        String id = UUID.randomUUID().toString();
        Content content = new Content(edPost.getText().toString(), tvTypePost.getText().toString(), type_post);

        HandleImage(uris, id, urls -> {
            Post post = new Post(id, PersonAPI.getInstance().getPersonId(), urls,
                    new ArrayList<>(), new ArrayList<>(),
                    content, new Timestamp(new Date()));

            firebaseDatabase.getReference("AllPost")
                    .child(PersonAPI.getInstance().getPersonId())
                    .child(post.getIdPost())
                    .setValue(post)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(CreatePostActivity.this
                                , "Đăng bài thành công", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e ->
                    Toast.makeText(CreatePostActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show());
        });
    }

    private void HandleImage(ArrayList<Uri> uris, String id, ListenerHandleImage listener) {
        ArrayList<String> url_image = new ArrayList<>();
        if (uris == null || uris.size() == 0) {
            listener.url_image(new ArrayList<>());
            return;
        }

        Observable<Uri> observable = Observable.from(uris);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uri>() {
                    int i = 0;

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CreatePostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Uri uri) {
                        String id_image = id.concat("_" + (i++));
                        storageRef.child(id_image)
                                .putFile(uri)
                                .addOnSuccessListener(taskSnapshot -> {
                                    Task<Uri> firebaseUri = taskSnapshot.getStorage()
                                            .getDownloadUrl();
                                    firebaseUri.addOnSuccessListener(uri1 -> {
                                        url_image.add(uri1.toString());
                                        if (url_image.size() == uris.size())
                                            listener.url_image(url_image);
                                    });
                                });
                    }
                });
    }

    private void SendPost() {
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnCreatePost:
                    UploadPostToFirebase();
                    onBackPressed();
                    break;
            }
            return false;
        });

    }

    private void TestChooseMultipleImage() {
        imgChooseImagePost.setOnClickListener(v -> {
            Intent intent = new Intent(CreatePostActivity.this, ActivityImageGallary.class);
            startActivityForResult(intent, REQUEST_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uris = (ArrayList<Uri>) data.getExtras().get(ActivityImageGallary.KEY_IMAGE);
                adapterChooseImage.setImages(uris);
                recyclerViewChooseImage.setVisibility(View.VISIBLE);
                linearShowTypePost.setVisibility(View.GONE);

                if (edPost.getText().toString().length() < 100
                        && adapterChooseImage.getItemCount() == 0) {
                    linearShowTypePost.setVisibility(View.VISIBLE);
                    recyclerViewChooseImage.setVisibility(View.GONE);
                }
            }
        }
    }
}