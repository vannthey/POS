package com.example.pos.dasboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragDashboardBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Frag_Dashboard extends Fragment {

    FragmentFragDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);

        /*
        Select QR icon in edite text // 11/2/2023
         */

        binding.searchViewDashboard.setOnTouchListener((view, motionEvent) -> {
            final int DRAWABLE_RIGHT = 0;
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (binding.searchViewDashboard.getRight() 
                        - binding.searchViewDashboard.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    Toast.makeText(requireContext(), "Scanning QR Code", Toast.LENGTH_SHORT).show();

                }
            }
            return false;
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
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("All"));
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("Drink"));
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("Soft Drink"));
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("Hot Drink"));
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("Food"));
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("Meal"));
        binding.tabLayoutOnDashboard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu,menu);
        menu.findItem(R.id.add_items_cart).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}