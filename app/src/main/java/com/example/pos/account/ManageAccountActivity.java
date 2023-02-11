package com.example.pos.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.databinding.ActivityManageAccountBinding;

public class ManageAccountActivity extends AppCompatActivity {

    ActivityManageAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageAccountBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.customActionbarManageAccount.customActionbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.customActionbarManageAccount.customActionbar.setNavigationOnClickListener(v -> finish());
        setTitle("Account Management");

        setContentView(binding.getRoot());
    }
}