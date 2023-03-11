package com.example.pos.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pos.Database.Entity.Category;
import com.example.pos.HelperClass.DateHelper;
import com.example.pos.R;
import com.example.pos.HelperClass.SharedPrefHelper;
import com.example.pos.databinding.FragmentFragCategoryBinding;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;
    CategoryViewModel categoryViewModel;
    String categoryName;
    int categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater, container, false);
        binding.formCategory.btnCancelCategory.setOnClickListener(this::CancelCategory);
        binding.formCategory.btnSaveCategory.setOnClickListener(this::SaveCategory);
        binding.formCategory.btnUpdateCategory.setOnClickListener(this::UpdateCategory);
        binding.formCategory.btnDeleteCategory.setOnClickListener(this::DeleteCategory);
        GetAllCategory();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void SaveCategory(View view) {
        if (!String.valueOf(binding.formCategory.categoryName.getText()).isEmpty()) {
            categoryViewModel.createCategory(new Category(String.valueOf(binding.formCategory.categoryName.getText()),
                    SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                    DateHelper.getCurrentDate()));
            OnUpdateUI();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Category_Name,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateCategory(View view) {
        if (binding.formCategory.categoryName.getText() != null) {
            categoryViewModel.updateCategoryById(categoryName =
                    String.valueOf(binding.formCategory.categoryName.getText()), categoryId);
            OnUpdateUI();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Category_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void DeleteCategory(View view) {
        categoryViewModel.deleteCategoryById(categoryId);
        OnUpdateUI();
    }

    private void CancelCategory(View view) {
        OnUpdateUI();
    }

    private void GetAllCategory() {
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
                binding.formCategory.categoryName.setText(categoryName);
                LayoutAddCategory();
                DeleteAndUpdate();

            });
        });
    }

    private void OnUpdateUI() {
        binding.formCategory.layoutAddCategory.setVisibility(View.GONE);
        binding.listCategory.setVisibility(View.VISIBLE);
        binding.formCategory.btnSaveCategory.setVisibility(View.VISIBLE);
        binding.formCategory.btnDeleteCategory.setVisibility(View.GONE);
        binding.formCategory.btnUpdateCategory.setVisibility(View.GONE);
        binding.formCategory.categoryName.setText(null);
    }

    private void DeleteAndUpdate() {
        binding.formCategory.btnDeleteCategory.setVisibility(View.VISIBLE);
        binding.formCategory.btnUpdateCategory.setVisibility(View.VISIBLE);
        binding.formCategory.btnSaveCategory.setVisibility(View.GONE);
    }

    private void LayoutAddCategory() {
        binding.formCategory.layoutAddCategory.setVisibility(View.VISIBLE);
        binding.listCategory.setVisibility(View.GONE);
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
                    binding.txtNoCategoryFound.setVisibility(View.GONE);
                    LayoutAddCategory();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }
}