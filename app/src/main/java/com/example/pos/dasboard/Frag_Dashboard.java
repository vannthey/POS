package com.example.pos.dasboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.MainActivity;
import com.example.pos.R;
import com.example.pos.databinding.BottomSheetDialogAddToCartBinding;
import com.example.pos.databinding.FragmentFragDashboardBinding;
import com.example.pos.product.ProductViewModel;
import com.example.pos.sale.SaleTransactionViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

public class Frag_Dashboard extends Fragment {
    ProductViewModel productViewModel;
    SaleTransactionViewModel saleTransactionViewModel;
    AdapterProductDashboard adapterProductDashboard;
    Handler handler;
    int cartCount = 0;
    int itemCount = 0;
    FragmentFragDashboardBinding binding;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialogAddToCartBinding addToCartBinding;
    String productImagePath;
    String productName;
    int productId;
    int productQty;
    int productUnit;
    double productPrice;
    double productDiscount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);
        handler = new Handler();
        saleTransactionViewModel = new ViewModelProvider(this).get(SaleTransactionViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        OnCheckProductSale();
        OnCreateMenu();
        OnGetAllProduct();
        OnTabLayout();
        OnBottomSheetDialog();
        /*
        Select QR icon in edite text // 11/2/2023
         */
        OnScanQR();

        return binding.getRoot();
    }

    private void OnCheckProductSale() {
        saleTransactionViewModel.getAllSaleTransaction().observe(getViewLifecycleOwner(), transactionList -> {
            if (transactionList.size() != 0) {
                binding.floatActionbarSale.setVisibility(View.VISIBLE);
                binding.floatActionbarSale.setText(String.valueOf(transactionList.size()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        productViewModel.getAllProduct().removeObservers(getViewLifecycleOwner());
        saleTransactionViewModel.getAllSaleTransaction().removeObservers(getViewLifecycleOwner());
        super.onDestroyView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void OnScanQR() {
        binding.searchViewDashboard.setOnTouchListener((view, motionEvent) -> {
            final int DRAWABLE_RIGHT = 0;
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (binding.searchViewDashboard.getRight() - binding.searchViewDashboard.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                }
            }
            return false;
        });
    }

    private void OnBottomSheetDialog() {
        addToCartBinding = BottomSheetDialogAddToCartBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(addToCartBinding.getRoot());

        addToCartBinding.sendProductToCartBottomSheet.setOnClickListener(v -> OnSendProductToCartBottomSheet());

        addToCartBinding.increaseProductQtyBottomSheet.setOnClickListener(v -> addToCartBinding.productQtyBottomSheet.setText(String.valueOf(itemCount += 1)));

        addToCartBinding.decreaseProductQtyBottomSheet.setOnClickListener(v -> OnCheckAddedProductQty());
    }

    private void OnCheckAddedProductQty() {
        if (itemCount > 0) {
            addToCartBinding.productQtyBottomSheet.setText(String.valueOf(itemCount -= 1));
        } else {
            addToCartBinding.productQtyBottomSheet.setText("0");
        }
    }

    private void OnSendProductToCartBottomSheet() {
        String getQty = String.valueOf(addToCartBinding.productQtyBottomSheet.getText());
        if (getQty.equals("0")) {
            Toast.makeText(requireContext(), "Cannot Add Product Cause Qty Is 0", Toast.LENGTH_SHORT).show();
        } else {
            productQty = Integer.parseInt(String.valueOf(addToCartBinding.productQtyBottomSheet.getText()));
            binding.floatActionbarSale.setVisibility(View.VISIBLE);
            addToCartBinding.productQtyBottomSheet.setText("0");
            bottomSheetDialog.dismiss();
            binding.floatActionbarSale.setText(String.valueOf(cartCount += 1));
            new Thread(() -> saleTransactionViewModel.createSaleTransaction(new SaleTransaction(productId, productName, productImagePath, productQty, productUnit, productPrice, productDiscount))).start();
        }
    }

    private void OnTabLayout() {
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("All"));
        binding.tabLayoutOnDashboard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*
    create Option menu in fragment using addMenuProvider and ViewLifecycleOwner to remove when view
    were
     destroy
     */
    private void OnCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.app_language).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.app_language) {
                    Toast.makeText(requireActivity(), "Changing Language", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnGetAllProduct() {
        productViewModel.getAllProduct().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                adapterProductDashboard = new AdapterProductDashboard(products, requireContext());
                binding.gridDashboard.setAdapter(adapterProductDashboard);
                binding.gridDashboard.setOnItemClickListener((adapterView, view, i, l) -> {
                    itemCount = 0;
                    addToCartBinding.productQtyBottomSheet.setText("0");
                    productImagePath = products.get(i).getImagePath();
                    productUnit = products.get(i).getProductUnitId();
                    productId = products.get(i).getProductId();
                    productPrice = products.get(i).getProductPrice();
                    productName = products.get(i).getProductName();
                    Glide.with(requireContext()).load(productImagePath).into(addToCartBinding.imageProductBottomSheet);
                    addToCartBinding.productNameBottomSheet.setText(products.get(i).getProductName());
                    bottomSheetDialog.show();
                });
                binding.searchViewDashboard.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapterProductDashboard.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }
        });
                 /*
    invoke method of navigation view in MainActivity to select Sale Fragment
     */
        binding.floatActionbarSale.setOnClickListener(view -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.SaleNavigator();
        });
    }


}