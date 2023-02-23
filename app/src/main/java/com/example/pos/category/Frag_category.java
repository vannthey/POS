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
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.POSDatabase;
import com.example.pos.Database.Relationship.CategoryWithSupplier;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragCategoryBinding;
import com.example.pos.supplier.AdapterSupplier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;
    SharedPreferences sharedPreferences;
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUsername = "Username";
    List<Category> categoryList;
    List<CategoryWithSupplier> categoryWithSupplierList;
    Category category;
    List<Supplier> supplierList;
    Handler handler;
    int SupplierId;

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
            categoryWithSupplierList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategoryFtSupplier();
            handler.post(() -> {
                binding.gridCategory.setAdapter(new AdapterCategoryFtSupplier(categoryWithSupplierList, requireContext()));
            });

        }).start();
    }

    private void onSaveCategory(View view) {
        String categoryName = String.valueOf(binding.categoryName.getText());
        String username = sharedPreferences.getString(SaveUsername, "");
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());
        String date = simpleDateFormat.format(current);
        if (categoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Category Name", Toast.LENGTH_SHORT).show();
        } else {
            category = new Category(categoryName, SupplierId, username, date);
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
            new Thread(() -> {
                supplierList =
                        POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSupplier();
                handler.post(() -> {
                    binding.spinnerSupplierCategory.setAdapter(new AdapterSupplier(supplierList,
                            requireContext()));
                });
            }).start();
            binding.spinnerSupplierCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    SupplierId = supplierList.get(i).getSupplierId();
                    Toast.makeText(requireContext(), "" + SupplierId, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    SupplierId = 0;
                }
            });
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