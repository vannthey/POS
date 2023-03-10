package com.example.pos.dasboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.MainActivity;
import com.example.pos.R;
import com.example.pos.category.CategoryViewModel;
import com.example.pos.databinding.FragmentFragDashboardBinding;
import com.example.pos.product.ProductViewModel;
import com.example.pos.sale.SaleTransactionViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Frag_Dashboard extends Fragment {
    ProductViewModel productViewModel;
    CategoryViewModel categoryViewModel;
    SaleTransactionViewModel saleTransactionViewModel;
    AdapterDashboard adapterDashboard;
    List<Category> categoryList;

    int itemCount = 0;
    FragmentFragDashboardBinding binding;
    String CategoryName;
    String productImagePath;
    String productName;
    int productId;
    int productUnit;
    double productPrice;
    List<SaleTransaction> saleTransactionList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        saleTransactionViewModel = new ViewModelProvider(this).get(SaleTransactionViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);
        GetProductInTransaction();
        GetCategory();
        GetAllProduct();
        OnCreateMenu();
        return binding.getRoot();
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
                if (tab.getText() != "All") {
                    adapterDashboard.getFilter().filter(tab.getText());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });//tab bar
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
    remove observer to prevent memory lack
     */
    @Override
    public void onDestroyView() {
        productViewModel.getAllProduct().removeObservers(getViewLifecycleOwner());
        saleTransactionViewModel.getAllSaleTransaction().removeObservers(getViewLifecycleOwner());
        categoryViewModel.getAllCategory().removeObservers(getViewLifecycleOwner());
        super.onDestroyView();
    }

    /*
    This method perform count decrease item that user add to cart
    */


    /*
    this method perform observer all product show in dashboard
     */
    private void GetAllProduct() {
        productViewModel.getAllProduct().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                adapterDashboard = new AdapterDashboard(products, requireContext());
                binding.gridDashboard.setAdapter(adapterDashboard);
                binding.gridDashboard.setOnItemClickListener((adapterView, view, i, l) -> {
                    itemCount = 0;
                    productImagePath = products.get(i).getImagePath();
                    productUnit = products.get(i).getProductUnitId();
                    productId = products.get(i).getProductId();
                    productPrice = products.get(i).getProductPrice();
                    productName = products.get(i).getProductName();
                    saleTransactionViewModel.createSaleTransaction(new SaleTransaction(productId, productName,
                            productImagePath, 1, productUnit, productPrice, 0, productPrice * 1));
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
        });
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
    }//option menu

}