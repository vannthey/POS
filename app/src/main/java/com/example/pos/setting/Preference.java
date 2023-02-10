package com.example.pos.setting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.databinding.ActivityPreferenceBinding;

public class Preference extends AppCompatActivity {

    ActivityPreferenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreferenceBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.customActionbarSetting.customActionbar);
        setTitle("Setting");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.customActionbarSetting.customActionbar.setNavigationOnClickListener(l->this.finish());

        setContentView(binding.getRoot());
    }

}