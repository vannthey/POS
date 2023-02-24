package com.example.pos.product;

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

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.Entity.Unit;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.category.AdapterCategory;
import com.example.pos.databinding.FragmentFragProductBinding;
import com.example.pos.inventory.AdapterInventory;
import com.example.pos.supplier.AdapterSupplier;
import com.example.pos.unit.AdapterUnit;

import java.util.List;
import java.util.Random;

public class Frag_Product extends Fragment {
    FragmentFragProductBinding binding;
    List<Product> productList;
    List<Inventory> inventoryList;
    List<Category> categoryList;
    List<Unit> unitList;
    List<Supplier> supplierList;
    Product product;
    Transition transition;
    Handler handler;
    Random rnd;
    String productName;
    int productQty;
    int inventoryId;
    int categoryId;
    int supplierId;
    int unitId;
    double productPrice;
    double productCost;
    double productTax;
    long productCode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragProductBinding.inflate(getLayoutInflater(), container, false);
        handler = new Handler();
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
        OnShowStateAddProduct();
    }

    private void OnShowStateAddProduct() {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
    }

    private void onSaveProduct(View view) {
        OnShowStateAddProduct();
        productCode = Integer.parseInt(String.valueOf(binding.addProductPrice.getText()));
        productQty = Integer.parseInt(String.valueOf(binding.addProductQty.getText()));
        productName = String.valueOf(binding.addProductName.getText());
        productPrice = Double.parseDouble(String.valueOf(binding.addProductPrice.getText()));
        productCost = Double.parseDouble(String.valueOf(binding.addProductCost.getText()));
        productTax = Double.parseDouble(String.valueOf(binding.addProductTax.getText()));
        product = new Product(productName, productQty, unitId, productCode, productCost, productPrice, productTax, inventoryId, categoryId, supplierId, SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate());
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createProduct(product);
            handler.post(this::onShowAllProduct);
        }).start();

    }

    private void onShowAllProduct() {
        handler = new Handler();
        new Thread(() -> {
            productList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
            handler.post(() -> binding.listShowProduct.setAdapter(new AdapterProduct(productList, requireContext())));
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_product) {
            transition = new Slide();
            TransitionManager.beginDelayedTransition(binding.layoutAddProduct, transition);
            binding.layoutAddProduct.setVisibility(binding.layoutAddProduct.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            binding.layoutShowProduct.setVisibility(binding.layoutShowProduct.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            OnGetAllSpinnerData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void OnGetAllSpinnerData() {
        new Thread(() -> {
            categoryList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategory();
            handler.post(() -> binding.spinnerProductCategory.setAdapter(new AdapterCategory(categoryList, requireContext())));
        }).start();
        new Thread(() -> {
            inventoryList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllInventory();

            handler.post(() -> binding.spinnerProductInventory.setAdapter(new AdapterInventory(inventoryList, requireContext())));
        }).start();
        new Thread(() -> {
            supplierList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSupplier();
            handler.post(() -> binding.spinnerProductSupplier.setAdapter(new AdapterSupplier(supplierList, requireContext())));
        }).start();
        new Thread(() -> {
            unitList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllUnit();
            handler.post(() -> binding.spinnerProductUnit.setAdapter(new AdapterUnit(unitList, requireContext())));
        }).start();
        binding.spinnerProductUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitId = unitList.get(i).getUnitId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerProductSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                supplierId = supplierList.get(i).getSupplierId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerProductInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inventoryId = inventoryList.get(i).inventoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryId = categoryList.get(i).categoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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