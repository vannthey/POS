package com.example.pos.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.databinding.ActivityUserDetailBinding;

public class UserDetail extends AppCompatActivity {

    ActivityUserDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(binding.customActionbarUserDetail.customActionbar);
        setTitle("User Detail");
        setContentView(binding.getRoot());
        binding.customActionbarUserDetail.customActionbar.setNavigationOnClickListener(v -> finish());
    }
}