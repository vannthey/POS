package com.example.pos.product;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.category.AdapterCategory;
import com.example.pos.databinding.FragmentFragProductBinding;
import com.example.pos.inventory.AdapterInventory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Frag_Product extends Fragment {
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUsername = "Username";
    FragmentFragProductBinding binding;
    List<Product> productList;
    List<Inventory> inventoryList;
    List<Category> categoryList;
    Category category;
    Product product;
    Transition transition;
    Handler handler;
    SharedPreferences sharedPreferences;
    Random rnd;
    String productName;
    String creator;
    String createDate;
    int productQty;
    int inventoryId;
    int categoryId;
    double productPrice;
    double productCost;
    double productTax;
    long productCode;
    SimpleDateFormat dateFormat;
    Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragProductBinding.inflate(getLayoutInflater(), container, false);
        binding.btnSaveProduct.setOnClickListener(this::onSaveProduct);
        binding.btnCancelAddProduct.setOnClickListener(this::onCancelSaveProduct);
        onShowAllProduct();
        binding.addProductCode.setOnClickListener(this::getRandomProductCode);
        return binding.getRoot();
    }

    private void getRandomProductCode(View view) {
        rnd = new Random();
        binding.addProductCode.setText(String.valueOf(rnd.nextInt(999999)));
    }

    private void onCancelSaveProduct(View view) {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
    }

    private void onSaveProduct(View view) {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
        date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        sharedPreferences = requireContext().getSharedPreferences(SaveUserLogin, 0);
        creator = sharedPreferences.getString(SaveUsername, "");
        createDate = dateFormat.format(date);

        productCode = Integer.parseInt(String.valueOf(binding.addProductPrice.getText()));
        productQty = Integer.parseInt(String.valueOf(binding.addProductQty.getText()));
        productName = String.valueOf(binding.addProductName.getText());
        productPrice = Double.parseDouble(String.valueOf(binding.addProductPrice.getText()));
        productCost = Double.parseDouble(String.valueOf(binding.addProductCost.getText()));
        productTax = Double.parseDouble(String.valueOf(binding.addProductTax.getText()));
        product = new Product(productName, productQty, productCode, productCost, productPrice,
                productTax, inventoryId, categoryId, creator, createDate);
        handler = new Handler();
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createProduct(product);
            handler.post(() -> {
                onShowAllProduct();
            });
        }).start();

    }

    private void onShowAllProduct() {
        handler = new Handler();
        new Thread(() -> {
            productList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
            handler.post(() -> {
                binding.listShowProduct.setAdapter(new AdapterProduct(productList, requireContext()));
            });
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_product) {
            transition = new Slide();
            TransitionManager.beginDelayedTransition(binding.layoutAddProduct, transition);
            binding.layoutAddProduct.setVisibility(binding.layoutAddProduct.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            binding.layoutShowProduct.setVisibility(binding.layoutShowProduct.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            OnGetAllInventory();
        }
        return super.onOptionsItemSelected(item);
    }

    private void OnGetAllInventory() {
        handler = new Handler();
        new Thread(() -> {
            categoryList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategory();
            handler.post(() -> {
                binding.spinnerProductCategory.setAdapter(new AdapterCategory(categoryList,
                        requireContext()));
            });
        }).start();
        new Thread(() -> {
            inventoryList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllInventory();

            handler.post(() -> {
                binding.spinnerProductInventory.setAdapter(new AdapterInventory(inventoryList, requireContext()));
            });
        }).start();
        binding.spinnerProductInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inventoryId = inventoryList.get(i).inventoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                inventoryId = Integer.parseInt(null);
            }
        });
        binding.spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryId = categoryList.get(i).categoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categoryId = Integer.parseInt(null);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_product).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}