package com.example.pos.dasboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.MainActivity;
import com.example.pos.R;
import com.example.pos.category.CategoryViewModel;
import com.example.pos.databinding.BottomSheetDialogAddToCartBinding;
import com.example.pos.databinding.CustomCheckPayTypeBinding;
import com.example.pos.databinding.FragmentFragDashboardBinding;
import com.example.pos.product.ProductViewModel;
import com.example.pos.sale.SaleTransactionViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Frag_Dashboard extends Fragment {
    ProductViewModel productViewModel;
    CategoryViewModel categoryViewModel;
    SaleTransactionViewModel saleTransactionViewModel;
    AdapterDashboard adapterDashboard;
    List<Category> categoryList;
    int cartCount = 0;
    int itemCount = 0;
    FragmentFragDashboardBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialogAddToCartBinding addToCartBinding;
    String CategoryName;
    String getQtyBottomSheet;
    String productImagePath;
    String productName;
    int productId;
    int productQty;
    int productUnit;
    double productPrice;
    double productDiscount = 0;
    List<SaleTransaction> saleTransactionList;

    CustomCheckPayTypeBinding checkPayTypeBinding;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);
        saleTransactionViewModel = new ViewModelProvider(this).get(SaleTransactionViewModel.class);
        GetProductInTransaction();
        GetCategory();
        GetAllProduct();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void GetCategory() {
        categoryViewModel.getAllCategory().observe(getViewLifecycleOwner(), categories -> {
            if (categories.size() != 0) {
                categoryList = categories;
                OnTabLayout();
            }
        });
    }

    /*
    this method perform observer all product in sale
     */
    private void GetProductInTransaction() {
        saleTransactionViewModel.getAllSaleTransaction().observe(getViewLifecycleOwner(), transactionList -> {
            if (transactionList.size() != 0) {
                saleTransactionList = transactionList;
                binding.floatActionbarSale.setVisibility(View.VISIBLE);
                binding.floatActionbarSale.setText(String.valueOf(transactionList.size()));
            }
        });
    }

    /*
    this method perform send data to sale transaction
     */
    private void SaveProductToTransaction() {
        getQtyBottomSheet = String.valueOf(addToCartBinding.productQtyBottomSheet.getText());
        if (getQtyBottomSheet.equals("0")) {
            Toast.makeText(requireContext(), "Cannot Add Product Cause Qty Is 0", Toast.LENGTH_SHORT).show();
        } else {
            productQty = Integer.parseInt(String.valueOf(addToCartBinding.productQtyBottomSheet.getText()));
            binding.floatActionbarSale.setVisibility(View.VISIBLE);
            addToCartBinding.productQtyBottomSheet.setText("0");
            bottomSheetDialog.dismiss();
            binding.floatActionbarSale.setText(String.valueOf(cartCount += 1));
            /*
            Check if there already have the same item in invoice if so just update the qty
             */
            saleTransactionViewModel.createSaleTransaction(new SaleTransaction(productId, productName, productImagePath, productQty, productUnit, productPrice, productDiscount));
            if (saleTransactionList != null) {
                for (SaleTransaction saleTransaction : saleTransactionList) {
                    if (saleTransaction.getProductId() == productId) {
                        Toast.makeText(requireContext(), "" + saleTransaction.getProductName(), Toast.LENGTH_SHORT).show();
                        saleTransactionViewModel.editProductOnSaleById(
                                productPrice, (saleTransaction.getProductQty() + productQty), productDiscount, productId
                        );
                    }
                }
            }

        }
    }

    /*
    remove observer to prevent memory lack
     */
    @Override
    public void onDestroyView() {
        productViewModel.getAllProduct().removeObservers(getViewLifecycleOwner());
        saleTransactionViewModel.getAllSaleTransaction().removeObservers(getViewLifecycleOwner());
        categoryViewModel.getAllCategory().removeObservers(getViewLifecycleOwner());
        super.onDestroyView();
    }

    //    @SuppressLint("ClickableViewAccessibility")
//    private void OnScanQR() {
//        binding.searchViewDashboard.setOnTouchListener((view, motionEvent) -> {
//            final int DRAWABLE_RIGHT = 0;
//            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                if (motionEvent.getRawX() >= (binding.searchViewDashboard.getRight() - binding.searchViewDashboard.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                }
//            }
//            return false;
//        });
//    }
    /*
    This method perform count increase item that user add to cart
    */
    private void IncreaseProductQty() {
        addToCartBinding.productQtyBottomSheet.setText(String.valueOf(itemCount += 1));
    }

    /*
    This method perform count decrease item that user add to cart
    */
    private void DecreaseProductQty() {
        if (itemCount > 0) {
            addToCartBinding.productQtyBottomSheet.setText(String.valueOf(itemCount -= 1));
        } else {
            addToCartBinding.productQtyBottomSheet.setText("0");
        }
    }

    /*
    this method perform observer all product show in dashboard
     */
    private void GetAllProduct() {
        productViewModel.getAllProduct().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                adapterDashboard = new AdapterDashboard(products, requireContext());
                binding.gridDashboard.setAdapter(adapterDashboard);
                binding.gridDashboard.setOnItemClickListener((adapterView, view, i, l) -> {
                    BottomSheetDialog();
                    itemCount = 0;
                    addToCartBinding.productQtyBottomSheet.setText("0");
                    productImagePath = products.get(i).getImagePath();
                    productUnit = products.get(i).getProductUnitId();
                    productId = products.get(i).getProductId();
                    productPrice = products.get(i).getProductPrice();
                    productName = products.get(i).getProductName();
                    Glide.with(requireContext()).load(productImagePath).into(addToCartBinding.imageProductBottomSheet);
                    addToCartBinding.productNameBottomSheet.setText(products.get(i).getProductName());
                    addToCartBinding.productPriceBottomSheet.setText(String.valueOf(productPrice));
                    bottomSheetDialog.show();
                });
                binding.searchViewDashboard.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapterDashboard.getFilter().filter(charSequence);
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
            ((MainActivity) requireActivity()).SaleNavigator();
//            checkPayTypeBinding = CustomCheckPayTypeBinding.inflate(getLayoutInflater());
//            builder = new AlertDialog.Builder(requireContext());
//            builder.setView(checkPayTypeBinding.getRoot());
//            alertDialog = builder.create();
//            checkPayTypeBinding.payByCard.setOnClickListener(view1 -> {
//                ((MainActivity) requireActivity()).SaleNavigator();
//                alertDialog.dismiss();
//            });
//            checkPayTypeBinding.payByCase.setOnClickListener(view1 -> {
//
//                alertDialog.dismiss();
//            });
//            alertDialog.show();
        });
    }

    /*
tab layout on dashboard
 */
    private void OnTabLayout() {
        binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText("All"));
        if (categoryList.size() != 0) {
            for (Category category : categoryList) {
                CategoryName = category.getCategoryName();
                binding.tabLayoutOnDashboard.addTab(binding.tabLayoutOnDashboard.newTab().setText(CategoryName));
            }
        }
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
this method perform operation bottom sheet bottomSheetDialog
 */
    private void BottomSheetDialog() {
        addToCartBinding = BottomSheetDialogAddToCartBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(addToCartBinding.getRoot());

        addToCartBinding.sendProductToCartBottomSheet.setOnClickListener(v -> SaveProductToTransaction());

        addToCartBinding.increaseProductQtyBottomSheet.setOnClickListener(v -> IncreaseProductQty());

        addToCartBinding.decreaseProductQtyBottomSheet.setOnClickListener(v -> DecreaseProductQty());
    }

    /*
create Option menu in fragment using addMenuProvider and ViewLifecycleOwner to remove when view were destroy
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
                    ((MainActivity) requireActivity()).ChangeLanguage();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

}