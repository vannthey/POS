package com.example.pos.report;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragReportBinding;


public class Frag_report extends Fragment {
    FragmentFragReportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragReportBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}