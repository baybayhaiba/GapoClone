package com.example.gapoclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.Main.MainActivity;
import com.example.gapoclone.Model.Person;
import com.example.gapoclone.Utilities.ImageSample;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Splashscreen extends AppCompatActivity {

    private TextView title;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        initAnimation();
        handleAccount();
        showLogo();
    }

    private void initAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splashscreen);
        title = findViewById(R.id.tv_title);
        title.setAnimation(animation);
    }

    private void showLogo() {
        new Handler(Looper.myLooper()).postDelayed(() -> {

        }, 10);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    private void handleAccount() {
        mAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            if (mAuth.getCurrentUser() != null) {
                db.collection("Person")
                        .document(mAuth.getCurrentUser().getUid())
                        .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Person person = task.getResult().toObject(Person.class);
                        PersonAPI.getInstance().setEmail(person.getEmail());
                        PersonAPI.getInstance().setName(person.getName());
                        PersonAPI.getInstance().setPersonId(person.getPersonId());
                        PersonAPI.getInstance().setPersonImg(person.getPersonImg());
                    }

                    if (task.isComplete())
                        startActivity(new Intent(Splashscreen.this, MainActivity.class));
                });
            } else {
                startActivity(new Intent(Splashscreen.this, ActivityLogin.class));
            }
        };

    }
}