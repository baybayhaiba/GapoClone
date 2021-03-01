package com.example.gapoclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.Model.Person;
import com.example.gapoclone.Model.Trend;
import com.example.gapoclone.Utilities.ImageSample;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityRegister extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText edEmail, edPassword, edRepassword, edName;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edEmail = findViewById(R.id.ed_email_account_register);
        edPassword = findViewById(R.id.ed_password_account_register);
        edRepassword = findViewById(R.id.ed_repassword_account_register);
        edName = findViewById(R.id.ed_name_account_register);
        Button button = findViewById(R.id.btn_register);
        TextView tvLogin = findViewById(R.id.tv_login);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Xin vui lòng chờ");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tiến hành ....");
        db = FirebaseFirestore.getInstance();

        button.setOnClickListener(v -> {
            progressDialog.show();
            auth.createUserWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            String uid = auth.getCurrentUser().getUid();
                            db.collection("Person")
                                    .document(uid)
                                    .set(new Person(uid, edName.getText().toString(),
                                            edEmail.getText().toString(), ImageSample.IMG1))
                                    .addOnSuccessListener(documentReference -> progressDialog.dismiss());
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText
                                (ActivityRegister.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });

        });
        tvLogin.setOnClickListener(v -> {
                    startActivity(new Intent(ActivityRegister.this, ActivityLogin.class));
                    finish();
                }
        );
    }
}