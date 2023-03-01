package com.example.pos.dasboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.POSDatabase;
import com.example.pos.MainActivity;
import com.example.pos.R;
import com.example.pos.databinding.BottomSheetDialogAddToCartBinding;
import com.example.pos.databinding.FragmentFragDashboardBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class Frag_Dashboard extends Fragment {
    List<Product> productList;
    Handler handler;
    int cartCount = 0;
    int itemCount = 0;
    FragmentFragDashboardBinding binding;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialogAddToCartBinding addToCartBinding;
    SaleTransaction saleTransaction;
    String productImagePath;
    String productName;
    int productId;
    int productQty;
    int productUnit;
    double productPrice;
    double productDiscount = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);
        handler = new Handler();
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        OnCreateMenu();
        OnGetAllProduct();
        OnAddItemToCart();
        OnTabLayout();
        OnBottomSheetDialog();
        /*
        Select QR icon in edite text // 11/2/2023
         */
        OnScanQR();

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void OnScanQR() {
        binding.searchViewDashboard.setOnTouchListener((view, motionEvent) -> {
            final int DRAWABLE_RIGHT = 0;
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (binding.searchViewDashboard.getRight()
                        - binding.searchViewDashboard.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    launchQRScan();
                }
            }
            return false;
        });
    }

    private void OnBottomSheetDialog() {
        addToCartBinding = BottomSheetDialogAddToCartBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(addToCartBinding.getRoot());

        addToCartBinding.sendProductToCartBottomSheet.setOnClickListener(v ->
                OnSendProductToCartBottomSheet());

        addToCartBinding.increaseProductQtyBottomSheet.setOnClickListener(v ->
                addToCartBinding.productQtyBottomSheet.setText(String.valueOf(itemCount += 1)));

        addToCartBinding.decreaseProductQtyBottomSheet.setOnClickListener(v ->
                OnCheckAddedProductQty());
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
            Toast.makeText(requireContext(), "Cannot Add Product Cause Qty Is 0",
                    Toast.LENGTH_SHORT).show();
        } else {
            productQty = Integer.parseInt(String.valueOf(addToCartBinding.productQtyBottomSheet.getText()));
            saleTransaction = new SaleTransaction(productId, productName, productImagePath,
                    productQty, productUnit, productPrice, productDiscount);
            binding.floatActionbarSale.setVisibility(View.VISIBLE);
            addToCartBinding.productQtyBottomSheet.setText("0");
            bottomSheetDialog.dismiss();
            binding.floatActionbarSale.setText(String.valueOf(cartCount += 1));
            new Thread(() -> POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createSaleTransaction(saleTransaction)).start();

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
        new Thread(() -> {
            productList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
            handler.post(() -> binding.gridDashboard.setAdapter(new AdapterProductDashboard(productList, requireContext())));
        }).start();
    }

    private void OnAddItemToCart() {
        binding.gridDashboard.setOnItemClickListener((adapterView, view, i, l) -> {
            itemCount = 0;
            addToCartBinding.productQtyBottomSheet.setText("0");
            productImagePath = productList.get(i).getImagePath();
            productUnit = productList.get(i).getProductUnitId();
            productId = productList.get(i).getProductId();
            productPrice = productList.get(i).getProductPrice();
            productName = productList.get(i).getProductName();
            Glide.with(requireContext()).load(productImagePath).into(addToCartBinding.imageProductBottomSheet);
            addToCartBinding.productNameBottomSheet.setText(productList.get(i).getProductName());
            bottomSheetDialog.show();
        });
        /*
        invoke method of navigation view in MainActivity to select Sale Fragment
         */
        binding.floatActionbarSale.setOnClickListener(view -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.SaleNavigator();
        });
    }

    private void launchQRScan() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
        options.setPrompt("Scanning Coding");
        options.setCameraId(0);  // Use a specific camera of the device
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setBarcodeImageEnabled(true);
        barcodeLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(requireContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });
}