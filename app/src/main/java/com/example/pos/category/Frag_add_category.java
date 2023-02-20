package com.example.pos.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragAddCategoryBinding;

public class Frag_add_category extends Fragment {
    FragmentFragAddCategoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragAddCategoryBinding.inflate(getLayoutInflater(),container,false);



        return binding.getRoot();
    }
}