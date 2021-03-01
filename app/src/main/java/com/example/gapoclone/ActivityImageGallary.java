package com.example.gapoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

import com.example.gapoclone.Adapter.AdapterImage;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityImageGallary extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
    private RecyclerView recyclerView;
    private AdapterImage adapterImage;
    private ArrayList<Uri> urisWereChoose;
    private ArrayList<Uri> uris;
    private TextView tvFinishChooseImage;
    public static final String KEY_IMAGE = "uri_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallary);
        tvFinishChooseImage = findViewById(R.id.tv_finish_choose_image);
        urisWereChoose = new ArrayList<>();
        uris = getImagesPath(this);
        CheckPermission();
        HandleChooseImage();
        HandleChooseImageFinish();
    }

    private void HandleChooseImageFinish() {
        tvFinishChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            intent.putExtra(KEY_IMAGE, urisWereChoose);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void CheckPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            readDataExternal();
        }
    }

    private void readDataExternal() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapterImage = new AdapterImage(this, uris);
        recyclerView.setAdapter(adapterImage);
    }

    private void HandleChooseImage() {
        adapterImage.setListenerImage((uri, isChecked) -> {
            if (isChecked)
                urisWereChoose.add(uri);
            else
                urisWereChoose.remove(uri);
        });
    }

    public static ArrayList<Uri> getImagesPath(Activity activity) {
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ArrayList<Uri> listOfAllImages = new ArrayList<Uri>();
        Cursor cursor;
        int column_index_id, column_index_folder_name;
        long imageId;

        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        if (cursor != null) {
            column_index_id = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                imageId = cursor.getLong(column_index_id);
                Uri PathOfImage = Uri.withAppendedPath(uri, "" + imageId);

                listOfAllImages.add(PathOfImage);
            }
            cursor.close();
        }

        Collections.reverse(listOfAllImages);

        return listOfAllImages;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    readDataExternal();
                }
                break;

            default:
                break;
        }
    }

}