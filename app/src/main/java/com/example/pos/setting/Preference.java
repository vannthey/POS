package com.example.pos.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pos.R;
import com.example.pos.databinding.ActivityPreferenceBinding;

public class Preference extends AppCompatActivity {

    ActivityPreferenceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreferenceBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
    }
}