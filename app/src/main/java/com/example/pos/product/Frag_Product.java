package com.example.pos.product;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.pos.Database.Entity.Product;
import com.example.pos.DateHelper;
import com.example.pos.R;
import com.example.pos.SharedPrefHelper;
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
    AdapterSupplier adapterSupplier;
    AdapterCategory adapterCategory;
    AdapterUnit adapterUnit;
    AdapterInventory adapterInventory;
    Random rnd;
    String productName;
    int productId;
    int productQty;
    int inventorySpinnerId;
    int categorySpinnerId;
    int supplierSpinnerId;
    int unitSpinnerId;
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
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        unitViewModel = new ViewModelProvider(this).get(UnitViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        supplierViewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        binding.btnSaveProduct.setOnClickListener(this::SaveProduct);
        binding.btnCancelAddProduct.setOnClickListener(this::CancelProduct);
        binding.btnUpdateProduct.setOnClickListener(this::UpdateProduct);
        binding.btnDeleteProduct.setOnClickListener(this::DeleteProduct);
        binding.addProductCode.setOnClickListener(this::getRandomProductCode);
        binding.addProductImage.setOnClickListener(v -> OnGetImage());
        GetAllProduct();
        GetSpinnerData();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void OnGetImage() {
        launcher.launch(ImagePicker.Companion.with(requireActivity()).maxResultSize(1080, 1080, true).crop().galleryOnly().createIntent()

        );

    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
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


    private void DeleteProduct(View view) {
        productViewModel.deleteProductById(productId);
        OnUpdateUI();
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }

    private void SaveProduct(View view) {
        if (String.valueOf(binding.addProductCode.getText()).isEmpty()
                || String.valueOf(binding.addProductQty.getText()).isEmpty()
                || String.valueOf(binding.addProductName.getText()).isEmpty()
                || String.valueOf(binding.addProductCost.getText()).isEmpty()
                || String.valueOf(binding.addProductTax.getText()).isEmpty()
                || String.valueOf(binding.addProductPrice.getText()).isEmpty()) {
            Toast.makeText(requireContext(), R.string.Please_Input_Product, Toast.LENGTH_SHORT).show();
        } else {
            productCode = Integer.parseInt(String.valueOf(binding.addProductCode.getText()));
            productQty = Integer.parseInt(String.valueOf(binding.addProductQty.getText()));
            productName = String.valueOf(binding.addProductName.getText());
            productPrice = Double.parseDouble(String.valueOf(binding.addProductPrice.getText()));
            productCost = Double.parseDouble(String.valueOf(binding.addProductCost.getText()));
            productTax = Double.parseDouble(String.valueOf(binding.addProductTax.getText()));
            productViewModel.createProduct(new Product(productName, productQty, unitSpinnerId, productCode, productCost, productPrice, productTax, inventorySpinnerId, categorySpinnerId, supplierSpinnerId, file.toString(), SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()), DateHelper.getCurrentDate()));
            OnUpdateUI();
        }
    }


    private void UpdateProduct(View view) {
        if (String.valueOf(binding.addProductCode.getText()).isEmpty()
                || String.valueOf(binding.addProductQty.getText()).isEmpty()
                || String.valueOf(binding.addProductName.getText()).isEmpty()
                || String.valueOf(binding.addProductCost.getText()).isEmpty()
                || String.valueOf(binding.addProductTax.getText()).isEmpty()
                || String.valueOf(binding.addProductPrice.getText()).isEmpty()) {
            Toast.makeText(requireContext(), R.string.Please_Input_Product, Toast.LENGTH_SHORT).show();
        } else {
            productCode = Integer.parseInt(String.valueOf(binding.addProductCode.getText()));
            productQty = Integer.parseInt(String.valueOf(binding.addProductQty.getText()));
            productName = String.valueOf(binding.addProductName.getText());
            productPrice = Double.parseDouble(String.valueOf(binding.addProductPrice.getText()));
            productCost = Double.parseDouble(String.valueOf(binding.addProductCost.getText()));
            productTax = Double.parseDouble(String.valueOf(binding.addProductTax.getText()));
            productViewModel.updateProductById(productName, productQty, unitSpinnerId, productCode, productCost, productPrice,
                    productTax, inventorySpinnerId, categorySpinnerId, supplierSpinnerId, file.toString(),
                    SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                    DateHelper.getCurrentDate(), productId);
            OnUpdateUI();
        }

    }

    private void getRandomProductCode(View view) {
        rnd = new Random();
        binding.addProductCode.setText(String.valueOf(rnd.nextInt(999999)));
    }

    private void CancelProduct(View view) {
        OnUpdateUI();
    }

    private void GetAllProduct() {
        productViewModel.getAllProduct().observe(getViewLifecycleOwner(), products -> {
            if (products.size() != 0) {
                binding.txtNoProductFound.setVisibility(View.GONE);
                binding.listShowProduct.setVisibility(View.VISIBLE);
                binding.listShowProduct.setAdapter(new AdapterProduct(products, requireContext()));
                binding.listShowProduct.setOnItemClickListener((adapterView, view, i, l) -> {
                    productId = products.get(i).getProductId();
                    categorySpinnerId = products.get(i).getCategoryId();
                    unitSpinnerId = products.get(i).getProductUnitId();
                    inventorySpinnerId = products.get(i).getInventoryId();
                    supplierSpinnerId = products.get(i).getSupplierId();
                    binding.addProductName.setText(products.get(i).getProductName());
                    binding.addProductCode.setText(String.valueOf(products.get(i).getProductCode()));
                    binding.addProductQty.setText(String.valueOf(products.get(i).getProductQty()));
                    binding.addProductTax.setText(String.valueOf(products.get(i).getProductTax()));
                    binding.addProductCost.setText(String.valueOf(products.get(i).getProductCost()));
                    binding.addProductPrice.setText(String.valueOf(products.get(i).getProductPrice()));
                    Glide.with(this).load(products.get(i).getImagePath()).into(binding.addProductImage);
                    LayoutSaveProduct();
                    DeleteAndUpdate();
                });
            }
        });

    }


    private void GetSpinnerData() {
        categoryViewModel.getAllCategory().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                adapterCategory = new AdapterCategory(categories, requireContext());
                binding.spinnerProductCategory.setAdapter(adapterCategory);
                binding.spinnerProductCategory.setSelection(adapterCategory.getPosition(categorySpinnerId));
                binding.spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        categorySpinnerId = categories.get(i).categoryId;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        categorySpinnerId = categories.get(0).categoryId;
                    }
                });
            }
        });
        inventoryViewModel.getAllInventory().observe(getViewLifecycleOwner(), inventories -> {
            if (inventories != null) {
                adapterInventory = new AdapterInventory(inventories, requireContext());
                binding.spinnerProductInventory.setAdapter(adapterInventory);
                binding.spinnerProductInventory.setSelection(adapterInventory.getPosition(inventorySpinnerId));
                binding.spinnerProductInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        inventorySpinnerId = inventories.get(i).inventoryId;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        inventorySpinnerId = inventories.get(0).inventoryId;
                    }
                });
            }
        });
        supplierViewModel.getAllSupplier().observe(getViewLifecycleOwner(), supplierList -> {
            if (supplierList != null) {
                adapterSupplier = new AdapterSupplier(supplierList, requireContext());
                binding.spinnerProductSupplier.setAdapter(adapterSupplier);
                binding.spinnerProductSupplier.setSelection(adapterSupplier.getPosition(supplierSpinnerId));
                binding.spinnerProductSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        supplierSpinnerId = supplierList.get(i).getSupplierId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        supplierSpinnerId = supplierList.get(0).getSupplierId();
                    }
                });
            }
        });
        unitViewModel.getAllUnit().observe(getViewLifecycleOwner(), unitList -> {
            if (unitList != null) {
                adapterUnit = new AdapterUnit(unitList, requireContext());
                binding.spinnerProductUnit.setAdapter(adapterUnit);
                binding.spinnerProductUnit.setSelection(adapterUnit.getPosition(unitSpinnerId));
                binding.spinnerProductUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        unitSpinnerId = unitList.get(i).getUnitId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        unitSpinnerId = unitList.get(0).getUnitId();
                    }
                });
            }
        });
    }

    private void DeleteAndUpdate() {
        binding.btnDeleteProduct.setVisibility(View.VISIBLE);
        binding.btnUpdateProduct.setVisibility(View.VISIBLE);
        binding.btnSaveProduct.setVisibility(View.GONE);
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

    private void LayoutSaveProduct() {
        binding.layoutAddProduct.setVisibility(View.VISIBLE);
        binding.layoutShowProduct.setVisibility(View.GONE);
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
                    LayoutSaveProduct();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }
}