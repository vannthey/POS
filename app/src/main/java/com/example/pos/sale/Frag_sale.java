package com.example.pos.sale;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.HelperClass.SharedPrefHelper;
import com.example.pos.MainActivity;
import com.example.pos.R;
import com.example.pos.customer.AdapterCustomer;
import com.example.pos.customer.CustomerHelper;
import com.example.pos.customer.CustomerViewModel;
import com.example.pos.databinding.CustomEditProductOnSaleBinding;
import com.example.pos.databinding.CustomSaleDiscountBinding;
import com.example.pos.databinding.FragmentFragSaleBinding;
import com.example.pos.databinding.SumarizeSaleChargeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.List;


public class Frag_sale extends Fragment implements doTransactionCallback, CustomerHelper {
    private final String TAG = "Product Quantity";
    DecimalFormat format;
    FragmentFragSaleBinding binding;
    CustomEditProductOnSaleBinding SaleBinding;
    CustomSaleDiscountBinding discountBinding;
    BottomSheetDialog bottomSheetDialog;
    SaleTransactionViewModel saleTransactionViewModel;
    AdapterSale adapterSale;
    CustomerViewModel customerViewModel;

    List<SaleTransaction> saleTransactionList;
    List<Product> productList;
    int riel = 4100;
    int customerId;
    int productId;
    double productPrice;
    double productDiscount;
    int productQty;
    double saleDiscount;
    double salSubTotal;
    BottomSheetDialog dialog;
    SumarizeSaleChargeBinding saleChargeBinding;
    AdapterCustomer adapterCustomer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        saleTransactionViewModel = new ViewModelProvider(this).get(SaleTransactionViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        format = new DecimalFormat("#,###.#");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragSaleBinding.inflate(inflater, container, false);
        binding.btnSalePay.setOnClickListener(v -> CheckOutTotal());
        GetAllSale();
        EditProductOnSale();
        OnCreateMenu();
        return binding.getRoot();
    }


    private double CalculateTotal(double subtotal, double saleDiscount) {
        return (subtotal - (saleDiscount / 100 * subtotal));
    }

    private void CheckOutTotal() {
        saleChargeBinding = SumarizeSaleChargeBinding.inflate(getLayoutInflater());
        dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(saleChargeBinding.getRoot());
        saleChargeBinding.sumarizeTotal.setText(String.valueOf(CalculateTotal(salSubTotal, saleDiscount)));
        saleChargeBinding.sumarizeTotalRiel.setText(format.format(CalculateTotal(salSubTotal, saleDiscount) * riel));
        saleChargeBinding.payByCard.setOnClickListener(view -> UpdateProductAfterSale());
        saleChargeBinding.payByCase.setOnClickListener(view -> UpdateProductAfterSale());
        dialog.show();
    }

    private void UpdateProductAfterSale() {
        for (SaleTransaction s : saleTransactionList) {
            new Thread(() -> {
                productQty = AppDatabase.getInstance(requireContext()).getDao().getProductQtyById(s.getProductId());
                AppDatabase.getInstance(requireContext()).getDao().updateProductQtyAfterSale(productQty - (s.getProductQty()), s.getProductId());
                saleTransactionViewModel.deleteAfterPay();
            }).start();
        }
    }

    private void GetAllSale() {
        saleTransactionViewModel.getAllSaleTransaction().observe(getViewLifecycleOwner(), transactionList -> {
            if (transactionList != null) {
                GetCustomer();
                saleTransactionList = transactionList;

                binding.addSaleDiscount.setOnClickListener(view -> addSaleDiscount());//add sale discount


                //initialize adapter to list view
                adapterSale = new AdapterSale(this, requireContext(), transactionList);
                binding.listItemSale.setAdapter(adapterSale);


                salSubTotal = adapterSale.UpdateTotal();//method update price from adapter

                binding.saleSubtotal.setText(String.valueOf(salSubTotal));//set subtotal before edit product
                binding.saleTotal.setText(String.valueOf(CalculateTotal(salSubTotal, saleDiscount)));//set Total


                binding.listItemSale.setOnItemClickListener((adapterView, view, i, l) -> {

                    productId = transactionList.get(i).getProductId();
                    Glide.with(requireContext()).load(transactionList.get(i).getProductImagePath()).into(SaleBinding.customEditImageOnSale);
                    SaleBinding.customEditProductPriceOnSale.setText(String.valueOf(transactionList.get(i).getProductPrice()));
                    SaleBinding.customEditProductDiscountOnSale.setText(String.valueOf(transactionList.get(i).getProductDiscount()));
                    SaleBinding.customEditProductQtyOnSale.setText(String.valueOf(transactionList.get(i).getProductQty()));


                    SaleBinding.customBtnSaveEditProduct.setOnClickListener(view1 -> {
                        productPrice = Double.parseDouble(String.valueOf(SaleBinding.customEditProductPriceOnSale.getText()));
                        productDiscount = Double.parseDouble(String.valueOf(SaleBinding.customEditProductDiscountOnSale.getText()));
                        productQty = Integer.parseInt(String.valueOf(SaleBinding.customEditProductQtyOnSale.getText()));
                        saleTransactionViewModel.editProductOnSaleById(productPrice, productQty, productDiscount,
                                (productPrice * productQty),
                                productId);
                        binding.saleTotal.setText(String.valueOf(CalculateTotal(salSubTotal, saleDiscount)));//set Total

                        binding.saleSubtotal.setText(String.valueOf(salSubTotal));//set subtotal after edit product
                        bottomSheetDialog.dismiss();

                    });
                    bottomSheetDialog.show();

                });
            }
        });
    }//GetAllSale


    private void addSaleDiscount() {
        discountBinding = CustomSaleDiscountBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(discountBinding.getRoot());
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        if (discountBinding.customSaleDiscount.getText() != null) {
            discountBinding.customSaveSaleDiscount.setOnClickListener(view -> {
                binding.saleDiscount.setText(String.valueOf(discountBinding.customSaleDiscount.getText()));
                saleDiscount = Double.parseDouble(String.valueOf(discountBinding.customSaleDiscount.getText()));
                binding.saleTotal.setText(String.valueOf(CalculateTotal(salSubTotal, saleDiscount)));//set Total
                //set total riel
                alertDialog.dismiss();
            });
        }
        discountBinding.customCancelSaleDiscount.setOnClickListener(view -> alertDialog.dismiss());
        alertDialog.show();
    }

    private void GetCustomer() {
        customerViewModel.getAllCustomer().observe(getViewLifecycleOwner(), customers -> {
            if (customers != null) {
                adapterCustomer = new AdapterCustomer(this, customers, requireContext());
                adapterCustomer.isVisible(0);
                binding.spinnerCustomerSale.setAdapter(adapterCustomer);
                binding.spinnerCustomerSale.setSelection(SharedPrefHelper.getInstance().getSaveCustomerIndex(requireContext()));
                binding.spinnerCustomerSale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        saleDiscount = customers.get(i).getCustomerDiscount();
                        SharedPrefHelper.getInstance().SaveCustomerSaleIndex(i, requireContext());
                        customerId = customers.get(i).getCustomerId();
                        binding.saleDiscount.setText(String.valueOf(saleDiscount));
                        binding.saleTotal.setText(String.valueOf(CalculateTotal(salSubTotal, saleDiscount)));//set Total
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }//Get Customer

    private void EditProductOnSale() {
        SaleBinding = CustomEditProductOnSaleBinding.inflate(getLayoutInflater());
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(SaleBinding.getRoot());
        SaleBinding.customBtnCancelEditProduct.setOnClickListener(v -> bottomSheetDialog.dismiss());

    }

    @Override
    public void doDelete(int i) {
        saleTransactionViewModel.deleteSaleTransactionById(i);
    }

    private void OnCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.go_dashboard).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.go_dashboard) {
                    ((MainActivity) requireActivity()).DashboardNavigator();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }//OnCreateMenu

    @Override
    public void doCustomizeCustomerById(int i) {

    }

}