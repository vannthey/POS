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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Product;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.category.AdapterCategory;
import com.example.pos.category.CategoryViewModel;
import com.example.pos.databinding.FragmentFragProductBinding;
import com.example.pos.inventory.AdapterInventory;
import com.example.pos.inventory.InventoryViewModel;
import com.example.pos.supplier.AdapterSupplier;
import com.example.pos.supplier.SupplierViewModel;
import com.example.pos.unit.AdapterUnit;
import com.example.pos.unit.UnitViewModel;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
import java.util.Random;

public class Frag_Product extends Fragment {
    FragmentFragProductBinding binding;
    ProductViewModel productViewModel;
    SupplierViewModel supplierViewModel;
    UnitViewModel unitViewModel;
    CategoryViewModel categoryViewModel;
    InventoryViewModel inventoryViewModel;
    Handler handler;
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
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        unitViewModel = new ViewModelProvider(this).get(UnitViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        supplierViewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        binding.btnSaveProduct.setOnClickListener(this::onSaveProduct);
        binding.btnCancelAddProduct.setOnClickListener(this::onCancelSaveProduct);
        binding.btnUpdateProduct.setOnClickListener(this::OnUpdateProduct);
        binding.btnDeleteProduct.setOnClickListener(this::OnDeleteProduct);
        binding.addProductCode.setOnClickListener(this::getRandomProductCode);
        binding.addProductImage.setOnClickListener(v -> OnGetImage());
        OnGetAllSpinnerData();
        onShowAllProduct();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void OnDeleteProduct(View view) {
        new Thread(() -> {
            productViewModel.deleteProductById(productId);
            handler.post(() -> {
                OnUpdateUI();
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }


    private void OnShowBtnDeleteUpdate() {
        binding.btnDeleteProduct.setVisibility(View.VISIBLE);
        binding.btnUpdateProduct.setVisibility(View.VISIBLE);
        binding.btnSaveProduct.setVisibility(View.GONE);
    }

    private void OnUpdateProduct(View view) {

        OnUpdateUI();
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
                    binding.btnDeleteProduct.setVisibility(View.GONE);
                    binding.btnUpdateProduct.setVisibility(View.GONE);
                    OnShowAddProduct();
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
        OnUpdateUI();
    }

    private void OnShowAddProduct() {
        binding.layoutAddProduct.setVisibility(View.VISIBLE);
        binding.layoutShowProduct.setVisibility(View.GONE);
    }

    private void onSaveProduct(View view) {
        productCode = Integer.parseInt(String.valueOf(binding.addProductPrice.getText()));
        productQty = Integer.parseInt(String.valueOf(binding.addProductQty.getText()));
        productName = String.valueOf(binding.addProductName.getText());
        productPrice = Double.parseDouble(String.valueOf(binding.addProductPrice.getText()));
        productCost = Double.parseDouble(String.valueOf(binding.addProductCost.getText()));
        productTax = Double.parseDouble(String.valueOf(binding.addProductTax.getText()));
        if (productName != null) {
            new Thread(() -> {
                productViewModel.createProduct(new Product(productName, productQty, unitId,
                        productCode, productCost, productPrice, productTax, inventoryId, categoryId, supplierId, file.toString(), SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate()));
                handler.post(this::OnUpdateUI);
            }).start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Product, Toast.LENGTH_SHORT).show();
        }

    }

    private void onShowAllProduct() {
        productViewModel.getAllProduct().observe(getViewLifecycleOwner(), products -> {
            if (products.size() != 0) {
                binding.txtNoProductFound.setVisibility(View.GONE);
                binding.listShowProduct.setVisibility(View.VISIBLE);
                binding.listShowProduct.setAdapter(new AdapterProduct(products
                        , requireContext()));
                binding.listShowProduct.setOnItemClickListener((adapterView, view, i, l) -> {
                    productId = products.get(i).getProductId();
                    binding.addProductName.setText(products.get(i).getProductName());
                    binding.addProductCode.setText(String.valueOf(products.get(i).getProductCode()));
                    binding.addProductQty.setText(String.valueOf(products.get(i).getProductQty()));
                    binding.addProductTax.setText(String.valueOf(products.get(i).getProductTax()));
                    binding.addProductCost.setText(String.valueOf(products.get(i).getProductCost()));
                    binding.addProductPrice.setText(String.valueOf(products.get(i).getProductPrice()));
                    binding.spinnerProductSupplier.setSelection(0);
                    binding.spinnerProductCategory.setSelection(0);
                    binding.spinnerProductUnit.setSelection(0);
                    binding.spinnerProductInventory.setSelection(0);
                    Glide.with(this).load(products.get(i).getImagePath()).into(binding.addProductImage);
                    OnShowAddProduct();
                    OnShowBtnDeleteUpdate();
                });
            }
        });

    }

    private void OnGetAllSpinnerData() {
        categoryViewModel.getAllCategory().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                binding.spinnerProductCategory.setAdapter(new AdapterCategory(categories, requireContext()));
                binding.spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        categoryId = categories.get(i).categoryId;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        categoryId = categories.get(0).categoryId;
                    }
                });
            }
        });
        inventoryViewModel.getAllInventory().observe(getViewLifecycleOwner(), inventories -> {
            if (inventories != null) {
                binding.spinnerProductInventory.setAdapter(new AdapterInventory(inventories,
                        requireContext()));
                binding.spinnerProductInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        inventoryId = inventories.get(i).inventoryId;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        inventoryId = inventories.get(0).inventoryId;
                    }
                });
            }
        });
        supplierViewModel.getAllSupplier().observe(getViewLifecycleOwner(), supplierList -> {
            if (supplierList != null) {
                binding.spinnerProductSupplier.setAdapter(new AdapterSupplier(supplierList,
                        requireContext()));
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
            }
        });
        unitViewModel.getAllUnit().observe(getViewLifecycleOwner(), unitList -> {
            if (unitList != null) {
                binding.spinnerProductUnit.setAdapter(new AdapterUnit(unitList, requireContext()));
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
            }
        });


    }

    public void OnUpdateUI() {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
        binding.btnDeleteProduct.setVisibility(View.GONE);
        binding.btnUpdateProduct.setVisibility(View.GONE);
        binding.btnSaveProduct.setVisibility(View.VISIBLE);
        binding.addProductName.setText(null);
        binding.addProductCode.setText(null);
        binding.addProductImage.setImageResource(R.drawable.ic_image);
        binding.addProductPrice.setText(null);
        binding.addProductTax.setText(null);
        binding.addProductQty.setText(null);
        binding.addProductCost.setText(null);
    }
}