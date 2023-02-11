package com.example.pos.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pos.databinding.FragmentFragReportBinding;

import java.util.ArrayList;
import java.util.List;


public class Frag_report extends Fragment {
    FragmentFragReportBinding binding;

    List<String> report_item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragReportBinding.inflate(inflater, container, false);
        report_item = new ArrayList<>();
        report_item.add("1");
        report_item.add("2");
        report_item.add("3");
        report_item.add("4");
        report_item.add("5");
        report_item.add("6");
        binding.showListReport.setAdapter(new report_adapter(report_item,requireContext()));

        binding.showListReport.setOnItemClickListener((adapterView, view, i, l) ->
                Toast.makeText(requireContext(), "Loading.......", Toast.LENGTH_SHORT).show());


        return binding.getRoot();
    }
}