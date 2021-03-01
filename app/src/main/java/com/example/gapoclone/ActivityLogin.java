package com.example.gapoclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {
    private static final String TAG = "LoginScreen";
    private EditText edEmail, edPassword;
    private Button btnLogin;
    private TextView tvLogin;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvLogin = findViewById(R.id.tv_register);
        btnLogin = findViewById(R.id.btn_login);
        edEmail = findViewById(R.id.ed_email_account_login);
        edPassword = findViewById(R.id.ed_password_account_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đăng nhập");
        progressDialog.setMessage("Đang xử lý....");
        progressDialog.setCancelable(true);
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));
        });
        btnLogin.setOnClickListener(v -> {
            progressDialog.show();
            auth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                    .addOnSuccessListener(authResult -> progressDialog.dismiss()).
                    addOnFailureListener(e -> {
                        Toast.makeText(ActivityLogin.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
        });
    }
}