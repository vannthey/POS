package com.example.pos.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragCategoryBinding;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}