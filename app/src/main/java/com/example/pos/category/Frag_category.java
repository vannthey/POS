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
        OnCreateMenu();
        return binding.getRoot();
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
                    binding.layoutAddCategory.setVisibility(View.VISIBLE);
                    binding.gridCategory.setVisibility(View.GONE);
                    binding.txtNoCategoryFound.setVisibility(View.GONE);
                }
                return true;
            }
        },getViewLifecycleOwner());
    }

    private void onShowAllCategory() {
        new Thread(() -> {
            categoryList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategory();
            handler.post(() -> {
                if (categoryList.size() != 0) {
                    binding.txtNoCategoryFound.setVisibility(View.GONE);
                    binding.gridCategory.setAdapter(new AdapterCategory(categoryList,
                            requireContext()));
                    binding.gridCategory.setVisibility(View.VISIBLE);
                }

            });

        }).start();
    }

    private void onSaveCategory(View view) {
        String categoryName = String.valueOf(binding.categoryName.getText());
        if (categoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Category Name", Toast.LENGTH_SHORT).show();
        } else {
            category = new Category(categoryName,
                    SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()),
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
}