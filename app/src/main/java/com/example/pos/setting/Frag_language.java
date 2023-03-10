package com.example.pos.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragLanguageBinding;

public class Frag_language extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFragLanguageBinding binding;
        binding = FragmentFragLanguageBinding.inflate(getLayoutInflater(), container, false);
        binding.radioLangGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.setLangToKh:
                    break;
                case R.id.setLangToEn:
                    break;

            }
        });
        return binding.getRoot();
    }
}