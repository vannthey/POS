package com.example.pos.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.FragmentFragCategoryBinding;

import java.util.List;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;
    SharedPreferences sharedPreferences;
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUsername = "Username";
    List<Category> categoryList;
    Category category;
    Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater, container, false);
        handler = new Handler();
        binding.btnCancelCategory.setOnClickListener(this::onCancelSaveCategory);
        binding.btnSaveCategory.setOnClickListener(this::onSaveCategory);
        sharedPreferences = requireContext().getSharedPreferences(SaveUserLogin, Context.MODE_PRIVATE);
        onShowAllCategory();

        return binding.getRoot();
    }

    private void onShowAllCategory() {
        new Thread(() -> {
            categoryList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategory();
            handler.post(() -> binding.gridCategory.setAdapter(new AdapterCategory(categoryList,
                    requireContext())));

        }).start();
    }

    private void onSaveCategory(View view) {
        String categoryName = String.valueOf(binding.categoryName.getText());
        if (categoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Category Name", Toast.LENGTH_SHORT).show();
        } else {
            category = new Category(categoryName,
                    SharedPreferenceHelper.getInstance(requireContext()).getSaveUserLoginName(requireContext()),
                    CurrentDateHelper.getCurrentDate());
            new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao()
                        .createCategory(category);
                handler.post(() -> {
                    onShowAllCategory();
                    binding.layoutAddCategory.setVisibility(View.GONE);
                    binding.gridCategory.setVisibility(View.VISIBLE);
                });
            }
            ).start();
        }


    }

    private void onCancelSaveCategory(View view) {
        binding.layoutAddCategory.setVisibility(View.GONE);
        binding.gridCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_category) {
            binding.layoutAddCategory.setVisibility(View.VISIBLE);
            binding.gridCategory.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_category).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}