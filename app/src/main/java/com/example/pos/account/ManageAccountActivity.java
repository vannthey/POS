package com.example.pos.account;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.R;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}