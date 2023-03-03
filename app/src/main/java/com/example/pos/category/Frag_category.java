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
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Category;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.FragmentFragCategoryBinding;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;
    SharedPreferences sharedPreferences;
    CategoryViewModel categoryViewModel;

    Handler handler;
    String categoryName;
    int categoryId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater, container, false);
        handler = new Handler();
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        binding.btnCancelCategory.setOnClickListener(this::onCancelSaveCategory);
        binding.btnSaveCategory.setOnClickListener(this::onSaveCategory);
        binding.btnUpdateCategory.setOnClickListener(this::onUpdateCategory);
        binding.btnDeleteCategory.setOnClickListener(this::onDeleteCategory);
        String saveUserLogin = "UserLogin";
        sharedPreferences = requireContext().getSharedPreferences(saveUserLogin, Context.MODE_PRIVATE);
        OnShowAllCategory();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void onDeleteCategory(View view) {
        new Thread(() -> {
            categoryViewModel.deleteCategoryById(categoryId);
            handler.post(this::OnUpdateUI);
        }).start();
    }

    private void OnUpdateUI() {
        binding.layoutAddCategory.setVisibility(View.GONE);
        binding.listCategory.setVisibility(View.VISIBLE);
        binding.btnSaveCategory.setVisibility(View.VISIBLE);
        binding.btnDeleteCategory.setVisibility(View.GONE);
        binding.btnUpdateCategory.setVisibility(View.GONE);
    }

    private void onUpdateCategory(View view) {
        new Thread(() -> {
            if (binding.categoryName.getText() != null) {
                categoryViewModel.updateCategoryById(categoryName =
                        String.valueOf(binding.categoryName.getText()), categoryId);
                handler.post(this::OnUpdateUI);
            } else {
                Toast.makeText(requireContext(), R.string.Please_Input_Category_Name, Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

    private void OnCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.add_category).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.add_category) {
                    onShowAddUnit();
                    binding.categoryName.setText(null);
                    binding.txtNoCategoryFound.setVisibility(View.GONE);
                    binding.btnSaveCategory.setVisibility(View.VISIBLE);
                    binding.btnDeleteCategory.setVisibility(View.GONE);
                    binding.btnUpdateCategory.setVisibility(View.GONE);
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnShowAllCategory() {
        categoryViewModel.getAllCategory().observe(getViewLifecycleOwner(), categories -> {
            if (categories.size() != 0) {
                binding.txtNoCategoryFound.setVisibility(View.GONE);
                binding.listCategory.setAdapter(new AdapterCategory(categories,
                        requireContext()));
                binding.listCategory.setVisibility(View.VISIBLE);
            }
            binding.listCategory.setOnItemClickListener((adapterView, view, i, l) -> {
                categoryId = categories.get(i).getCategoryId();
                categoryName = categories.get(i).getCategoryName();
                binding.categoryName.setText(categoryName);
                onShowAddUnit();
                onShowDeleteUpdate();

            });
        });
    }


    private void onSaveCategory(View view) {
        if (binding.categoryName.getText() != null) {

            new Thread(() -> {
                categoryViewModel.createCategory(new Category(String.valueOf(binding.categoryName.getText()),
                        SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()),
                        CurrentDateHelper.getCurrentDate()));
                handler.post(this::OnUpdateUI);
            }).start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Category_Name,
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void onShowDeleteUpdate() {
        binding.btnDeleteCategory.setVisibility(View.VISIBLE);
        binding.btnUpdateCategory.setVisibility(View.VISIBLE);
        binding.btnSaveCategory.setVisibility(View.GONE);
    }

    private void onShowAddUnit() {
        binding.layoutAddCategory.setVisibility(View.VISIBLE);
        binding.listCategory.setVisibility(View.GONE);
    }

    private void onCancelSaveCategory(View view) {
        OnUpdateUI();
    }
}