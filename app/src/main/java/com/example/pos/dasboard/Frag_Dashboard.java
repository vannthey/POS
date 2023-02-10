package com.example.pos.dasboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pos.databinding.FragmentFragDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class Frag_Dashboard extends Fragment {

    FragmentFragDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);

        binding.searchViewDashboard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        List<DashboardModel> models = new ArrayList<>();

        models.add(new DashboardModel("Coca Cola"));
        models.add(new DashboardModel("Samurai"));
        models.add(new DashboardModel("Red Bull"));
        models.add(new DashboardModel("Coca Cola"));
        models.add(new DashboardModel("Samurai"));
        models.add(new DashboardModel("Red Bull"));
        models.add(new DashboardModel("Coca Cola"));
        models.add(new DashboardModel("Samurai"));
        models.add(new DashboardModel("Red Bull"));
        binding.gridDashboard.setAdapter(new DashboardAdapter(models, getContext()));

        binding.gridDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(requireActivity(), "Click", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

}