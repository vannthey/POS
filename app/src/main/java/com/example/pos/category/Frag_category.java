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

        item_category.add(new CategoryModel("Drink"));
        item_category.add(new CategoryModel("Food"));
        item_category.add(new CategoryModel("Meal"));
        item_category.add(new CategoryModel("Snack"));
        item_category.add(new CategoryModel("Calory"));
        item_category.add(new CategoryModel("Vegetable"));
        item_category.add(new CategoryModel("Beef"));
        item_category.add(new CategoryModel("Beer"));

        binding.gridCategory.setAdapter(new CategoryAdapter(item_category, requireContext()));

        return binding.getRoot();
    }

}