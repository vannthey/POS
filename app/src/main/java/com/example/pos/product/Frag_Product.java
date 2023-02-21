package com.example.pos.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragProductBinding;

public class Frag_Product extends Fragment {
    FragmentFragProductBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragProductBinding.inflate(getLayoutInflater(), container, false);



        return binding.getRoot();
    }
}