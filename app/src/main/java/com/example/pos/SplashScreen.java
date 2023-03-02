package com.example.pos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.databinding.ActivitySpashScreenBinding;
import com.example.pos.setting.Login;

public class SplashScreen extends AppCompatActivity {
    ActivitySpashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler().post(() -> {
            startActivity(new Intent(this, Login.class));
            this.finish();
        });
    }
}