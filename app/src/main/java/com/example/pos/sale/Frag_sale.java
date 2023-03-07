package com.example.pos.sale;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.databinding.CustomEditProductOnSaleBinding;
import com.example.pos.databinding.FragmentFragSaleBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class Frag_sale extends Fragment implements DeleteProductCallBack {
    FragmentFragSaleBinding binding;
    CustomEditProductOnSaleBinding SaleBinding;
    BottomSheetDialog bottomSheetDialog;
    SaleTransactionViewModel saleTransactionViewModel;
    List<SaleTransaction> saleTransactionList;
    int productId;
    double productPrice;
    double productDiscount;
    int productQty;
    Handler handler;
    double saleDiscount;
    double saleSubtotal;
    double saleProductDiscount;
    double saleProductPrice;
    int saleProductQty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        handler = new Handler();
        saleTransactionViewModel = new ViewModelProvider(this).get(SaleTransactionViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragSaleBinding.inflate(inflater, container, false);
        binding.btnSalePay.setOnClickListener(this::SalePay);
        GetAllSale();
        BottomDialog();
        return binding.getRoot();
    }

    private void CalculateTotal() {
        if (saleTransactionList != null) {
            for (SaleTransaction saleTransaction : saleTransactionList) {
                saleProductDiscount = saleTransaction.getProductDiscount() / 100;
                saleProductQty = saleTransaction.getProductQty();
                saleProductPrice = saleTransaction.getProductPrice();
                saleProductDiscount = saleTransaction.getProductDiscount();
                //Find Subtotal
                if (saleProductDiscount == 0) {
                    saleSubtotal += (saleProductPrice * saleProductQty);
                    SetFinalPrice();
                } else {
                    saleSubtotal += (saleProductPrice * saleProductQty) - saleProductDiscount;
                    SetFinalPrice();
                }
            }
        }
    }


    private void ResetData() {
        saleSubtotal = 0;
        saleProductDiscount = 0;
        saleProductPrice = 0;
        saleProductQty = 0;
        saleDiscount = 0;
    }

    private void SetFinalPrice() {
        binding.saleTotal.setText(String.valueOf(saleSubtotal));
        binding.saleDiscount.setText("");
        binding.saleSubtotal.setText(String.valueOf(saleSubtotal));
    }

    private void SalePay(View view) {
        new Thread(() -> {
            saleTransactionViewModel.deleteAfterPay();
            handler.post(this::GetAllSale);
        }).start();
        ClearSaleTotal();
    }

    private void ClearSaleTotal() {
        binding.saleTotal.setText("");
        binding.saleDiscount.setText("");
        binding.saleSubtotal.setText("");
    }

    private void BottomDialog() {
        SaleBinding = CustomEditProductOnSaleBinding.inflate(getLayoutInflater());
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(SaleBinding.getRoot());
        SaleBinding.customBtnCancelEditProduct.setOnClickListener(v -> bottomSheetDialog.dismiss());

    }


    private void GetAllSale() {
        saleTransactionViewModel.getAllSaleTransaction().observe(getViewLifecycleOwner(),
                transactionList -> {
                    if (transactionList != null) {
                        saleTransactionList = transactionList;
                        binding.listItemSale.setAdapter(new AdapterSale(this, requireContext(),
                                transactionList));
                        binding.listItemSale.setOnItemClickListener((adapterView, view, i, l) -> {
                            productId = transactionList.get(i).getProductId();
                            Glide.with(requireContext()).load(transactionList.get(i).getProductImagePath()).into(SaleBinding.customEditImageOnSale);
                            SaleBinding.customEditProductPriceOnSale.setText(String.valueOf(transactionList.get(i).getProductPrice()));
                            SaleBinding.customEditProductDiscountOnSale.setText(String.valueOf(transactionList.get(i).getProductDiscount()));
                            SaleBinding.customEditProductQtyOnSale.setText(String.valueOf(transactionList.get(i).getProductQty()));
                            SaleBinding.customBtnSaveEditProduct.setOnClickListener(view1 -> {
                                productPrice =
                                        Double.parseDouble(String.valueOf(SaleBinding.customEditProductPriceOnSale.getText()));
                                productDiscount =
                                        Double.parseDouble(String.valueOf(SaleBinding.customEditProductDiscountOnSale.getText()));
                                productQty =
                                        Integer.parseInt(String.valueOf(SaleBinding.customEditProductQtyOnSale.getText()));
                                new Thread(() -> {
                                    saleTransactionViewModel.editProductOnSaleById
                                            (productPrice, productQty, productDiscount, productId);
                                    handler.post(() -> {
                                        bottomSheetDialog.dismiss();
                                        ClearSaleTotal();
                                        ResetData();
                                        GetAllSale();
                                    });
                                }).start();

                            });
                            bottomSheetDialog.show();
                        });
                    }
                });
        CalculateTotal();
    }

    @Override
    public void doDelete(int id) {
        new Thread(() -> saleTransactionViewModel.deleteSaleTransactionById(id)).start();
    }
}