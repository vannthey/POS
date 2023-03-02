package com.example.pos.product;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
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
    Handler handler;
    Thread thread;
    Random rnd;
    String productName;
    int productId;
    int productQty;
    int inventoryId;
    int categoryId;
    int supplierId;
    int unitId;
    double productPrice;
    double productCost;
    double productTax;
    long productCode;
    File file;
    Uri uri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragProductBinding.inflate(getLayoutInflater(), container, false);
        handler = new Handler();
        binding.btnSaveProduct.setOnClickListener(this::onSaveProduct);
        binding.btnCancelAddProduct.setOnClickListener(this::onCancelSaveProduct);
        binding.btnUpdateProduct.setOnClickListener(this::OnUpdateProduct);
        binding.btnDeleteProduct.setOnClickListener(this::OnDeleteProduct);
        binding.addProductCode.setOnClickListener(this::getRandomProductCode);
        binding.addProductImage.setOnClickListener(v -> OnGetImage());
        onShowAllProduct();
        OnGetAllSpinnerData();
        OnCreateMenu();
        OnClickOptionProduct();
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        thread.interrupt();
        super.onDetach();
    }

    private void OnDeleteProduct(View view) {
        thread = new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().deleteProductById(productId);
            handler.post(() -> Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show());
        });
        thread.start();
        OnHideAddProduct();
        onShowAllProduct();
    }

    private void OnClickOptionProduct() {
        binding.listShowProduct.setOnItemClickListener((adapterView, view, i, l) -> {
            OnShowAddProduct();
            OnShowBtnDeleteUpdate();
            productId = productList.get(i).getProductId();
            binding.addProductName.setText(productList.get(i).getProductName());
            binding.addProductCode.setText(String.valueOf(productList.get(i).getProductCode()));
            binding.addProductQty.setText(String.valueOf(productList.get(i).getProductQty()));
            binding.addProductTax.setText(String.valueOf(productList.get(i).getProductTax()));
            binding.addProductCost.setText(String.valueOf(productList.get(i).getProductCost()));
            binding.addProductPrice.setText(String.valueOf(productList.get(i).getProductPrice()));
            binding.spinnerProductSupplier.setSelection(0);
            binding.spinnerProductCategory.setSelection(0);
            binding.spinnerProductUnit.setSelection(0);
            binding.spinnerProductInventory.setSelection(0);
            Glide.with(this).load(productList.get(i).getImagePath()).into(binding.addProductImage);
        });
    }

    private void OnShowBtnDeleteUpdate() {
        binding.btnDeleteProduct.setVisibility(View.VISIBLE);
        binding.btnUpdateProduct.setVisibility(View.VISIBLE);
        binding.btnSaveProduct.setVisibility(View.GONE);
    }

    private void OnHideBtnDeleteUpdate() {
        binding.btnDeleteProduct.setVisibility(View.GONE);
        binding.btnUpdateProduct.setVisibility(View.GONE);
        binding.btnSaveProduct.setVisibility(View.VISIBLE);
    }

    private void OnUpdateProduct(View view) {
        OnHideAddProduct();
    }

    private void OnCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.add_product).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.add_product) {
                    binding.txtNoProductFound.setVisibility(View.GONE);
                    OnShowAddProduct();
                    OnClearAllDataInView();
                    OnHideBtnDeleteUpdate();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnGetImage() {
        launcher.launch(
                ImagePicker.Companion.with(requireActivity())
                        .maxResultSize(1080, 1080, true)
                        .crop().galleryOnly()
                        .createIntent()

        );

    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    uri = result.getData().getData();
                    file = new File(uri.getPath());
                    // Use the uri to load the image
                    binding.addProductImage.setImageURI(uri);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(requireContext(), "No Image Pick", Toast.LENGTH_SHORT).show();
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });


    private void getRandomProductCode(View view) {
        rnd = new Random();
        binding.addProductCode.setText(String.valueOf(rnd.nextInt(999999)));
    }

    private void onCancelSaveProduct(View view) {
        OnHideAddProduct();
        OnClearAllDataInView();
    }

    private void OnClearAllDataInView() {
        binding.addProductName.setText(null);
        binding.addProductCode.setText(null);
        binding.addProductImage.setImageResource(R.drawable.ic_image);
        binding.addProductPrice.setText(null);
        binding.addProductTax.setText(null);
        binding.addProductQty.setText(null);
        binding.addProductCost.setText(null);
    }

    private void OnShowAddProduct() {
        binding.layoutAddProduct.setVisibility(View.VISIBLE);
        binding.layoutShowProduct.setVisibility(View.GONE);
    }

    private void OnHideAddProduct() {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
    }

    private void onSaveProduct(View view) {
        OnHideAddProduct();
        productCode = Integer.parseInt(String.valueOf(binding.addProductPrice.getText()));
        productQty = Integer.parseInt(String.valueOf(binding.addProductQty.getText()));
        productName = String.valueOf(binding.addProductName.getText());
        productPrice = Double.parseDouble(String.valueOf(binding.addProductPrice.getText()));
        productCost = Double.parseDouble(String.valueOf(binding.addProductCost.getText()));
        productTax = Double.parseDouble(String.valueOf(binding.addProductTax.getText()));
        product = new Product(productName, productQty, unitId, productCode, productCost, productPrice, productTax, inventoryId, categoryId, supplierId, file.toString(), SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate());
        thread = new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createProduct(product);
            handler.post(this::onShowAllProduct);
        });
        thread.start();

    }

    private void onShowAllProduct() {
        thread = new Thread(() -> {
            productList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
            handler.post(() -> {
                if (productList.size() != 0) {
                    binding.listShowProduct.setAdapter(new AdapterProduct(productList
                            , requireContext()));
                    binding.txtNoProductFound.setVisibility(View.GONE);
                    binding.listShowProduct.setVisibility(View.VISIBLE);
                }
            });
        });
        thread.start();
    }

    private void OnGetAllSpinnerData() {
        thread = new Thread(() -> {
            categoryList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllCategory();
            handler.post(() -> binding.spinnerProductCategory.setAdapter(new AdapterCategory(categoryList, requireContext())));
        });
        thread.start();
        thread = new Thread(() -> {
            inventoryList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllInventory();
            handler.post(() -> binding.spinnerProductInventory.setAdapter(new AdapterInventory(inventoryList, requireContext())));
        });
        thread.start();
        thread = new Thread(() -> {
            supplierList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSupplier();
            handler.post(() -> binding.spinnerProductSupplier.setAdapter(new AdapterSupplier(supplierList, requireContext())));
        });
        thread.start();
        thread = new Thread(() -> {
            unitList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllUnit();
            handler.post(() -> binding.spinnerProductUnit.setAdapter(new AdapterUnit(unitList, requireContext())));
        });
        thread.start();
        binding.spinnerProductUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitId = unitList.get(i).getUnitId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                unitId = unitList.get(0).getUnitId();
            }
        });
        binding.spinnerProductSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                supplierId = supplierList.get(i).getSupplierId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                supplierId = supplierList.get(0).getSupplierId();
            }
        });
        binding.spinnerProductInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inventoryId = inventoryList.get(i).inventoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                inventoryId = inventoryList.get(0).inventoryId;
            }
        });
        binding.spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryId = categoryList.get(i).categoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categoryId = categoryList.get(0).categoryId;
            }
        });
    }
}