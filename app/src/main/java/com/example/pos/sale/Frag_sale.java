package com.example.pos.sale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragSaleBinding;


public class Frag_sale extends Fragment {
    FragmentFragSaleBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentFragSaleBinding.inflate(inflater, container, false);

       return binding.getRoot();
    }
}