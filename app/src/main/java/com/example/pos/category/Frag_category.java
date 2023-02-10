package com.example.pos.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pos.databinding.FragmentFragCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater, container, false);

        List<CategoryModel> item_category = new ArrayList<>();

        item_category.add(new CategoryModel("Drink", "1"));
        item_category.add(new CategoryModel("Food", "2"));
        item_category.add(new CategoryModel("Meal", "3"));
        item_category.add(new CategoryModel("Snack", "4"));
        item_category.add(new CategoryModel("Calory", "5"));
        item_category.add(new CategoryModel("Vegetable", "6"));
        item_category.add(new CategoryModel("Beef", "7"));
        item_category.add(new CategoryModel("Beer", "8"));

        binding.gridCategory.setAdapter(new CategoryAdapter(item_category, requireContext()));

        return binding.getRoot();
    }

}