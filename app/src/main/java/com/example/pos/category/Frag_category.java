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
    List<Category> categoryList;
    Category category;

    Handler handler;
    Thread thread;
    String categoryName;
    int categoryId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater, container, false);
        handler = new Handler();
        binding.btnCancelCategory.setOnClickListener(this::onCancelSaveCategory);
        binding.btnSaveCategory.setOnClickListener(this::onSaveCategory);
        binding.btnUpdateCategory.setOnClickListener(this::onUpdateCategory);
        binding.btnDeleteCategory.setOnClickListener(this::onDeleteCategory);
        sharedPreferences = requireContext().getSharedPreferences(SaveUserLogin, Context.MODE_PRIVATE);
        OnShowAllCategory();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void onDeleteCategory(View view) {
        thread = new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().deleteCategoryById(categoryId);
            handler.post(() -> {
                onHideAddUnit();
                OnShowAllCategory();
            });
        });
        thread.start();
    }

    private void onUpdateCategory(View view) {
        onGetData();
        thread = new Thread(() -> {
            if (binding.categoryName.getText() != null) {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().updateCategoryById(categoryName, categoryId);
                handler.post(() -> {
                    onHideAddUnit();
                    OnShowAllCategory();
                });
            } else {
                Toast.makeText(requireContext(), R.string.Please_Input_Category_Name, Toast.LENGTH_SHORT).show();
            }
        });
        thread.start();
    }

    @Override
    public void onDetach() {
        thread.interrupt();
        super.onDetach();
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
                    onHideDeleteUpdate();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnShowAllCategory() {
        thread = new Thread(() -> {
            categoryList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategory();
            handler.post(() -> {
                if (categoryList.size() != 0) {
                    binding.txtNoCategoryFound.setVisibility(View.GONE);
                    binding.listCategory.setAdapter(new AdapterCategory(categoryList,
                            requireContext()));
                    binding.listCategory.setVisibility(View.VISIBLE);
                }
                onClickCategory();
            });

        });
        thread.start();
    }

    private void OnGetData() {
        categoryName = String.valueOf(binding.categoryName.getText());
    }

    private void onSaveCategory(View view) {
        OnGetData();
        if (binding.categoryName.getText() != null) {
            category = new Category(categoryName,
                    SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()),
                    CurrentDateHelper.getCurrentDate());
            thread = new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao()
                        .createCategory(category);
                handler.post(() -> {
                    OnShowAllCategory();
                    onHideAddUnit();
                });
            });
            thread.start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Category_Name,
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void onClickCategory() {
        binding.listCategory.setOnItemClickListener((adapterView, view, i, l) -> {
            categoryId = categoryList.get(i).getCategoryId();
            categoryName = categoryList.get(i).getCategoryName();
            onSetData();
            onShowAddUnit();
            onShowDeleteUpdate();

        });
    }

    private void onSetData() {
        binding.categoryName.setText(categoryName);
    }

    private void onGetData() {
        categoryName = String.valueOf(binding.categoryName.getText());
    }

    private void onHideDeleteUpdate() {
        binding.btnSaveCategory.setVisibility(View.VISIBLE);
        binding.btnDeleteCategory.setVisibility(View.GONE);
        binding.btnUpdateCategory.setVisibility(View.GONE);
    }

    private void onShowDeleteUpdate() {
        binding.btnDeleteCategory.setVisibility(View.VISIBLE);
        binding.btnUpdateCategory.setVisibility(View.VISIBLE);
        binding.btnSaveCategory.setVisibility(View.GONE);
    }

    private void onHideAddUnit() {
        binding.layoutAddCategory.setVisibility(View.GONE);
        binding.listCategory.setVisibility(View.VISIBLE);
    }

    private void onShowAddUnit() {
        binding.layoutAddCategory.setVisibility(View.VISIBLE);
        binding.listCategory.setVisibility(View.GONE);
    }

    private void onCancelSaveCategory(View view) {
        onHideAddUnit();
    }
}