package com.example.pos.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.MainActivity;
import com.example.pos.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btnLogin.setOnClickListener(this::btnLogin);

    }

    private void btnLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}