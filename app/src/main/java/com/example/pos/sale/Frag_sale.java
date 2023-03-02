package com.example.pos.sale;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.POSDatabase;
import com.example.pos.databinding.CustomEditProductOnSaleBinding;
import com.example.pos.databinding.FragmentFragSaleBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class Frag_sale extends Fragment implements DeleteProductCallBack {
    FragmentFragSaleBinding binding;
    CustomEditProductOnSaleBinding SaleBinding;
    BottomSheetDialog bottomSheetDialog;
    List<SaleTransaction> transactionList;
    int productId;
    double productPrice;
    double productDiscount;
    int productQty;
    Handler handler;
    double saleTotal;
    double saleDiscount;
    double saleSubtotal;
    double saleProductDiscount;
    double saleProductPrice;
    int saleProductQty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragSaleBinding.inflate(inflater, container, false);
        binding.btnSalePay.setOnClickListener(this::OnSalePay);
        handler = new Handler();
        onGetAllSaleProduct();
        onCreateBottomDialog();
        return binding.getRoot();
    }

    private void onCalculateProductSale() {
        for (int i = 0; i < transactionList.size(); i++) {
            saleProductDiscount = transactionList.get(i).getProductDiscount() / 100;
            saleProductQty = transactionList.get(i).getProductQty();
            saleProductPrice = transactionList.get(i).getProductPrice();
            saleProductDiscount = transactionList.get(i).getProductDiscount();
            //Find Subtotal
            if (saleProductDiscount == 0) {
                saleSubtotal += (saleProductPrice * saleProductQty);
                onSetFinalPrice();
            } else  {
                saleSubtotal += (saleProductPrice * saleProductQty) - saleProductDiscount;
                onSetFinalPrice();
            }
        }
    }

    private void onClickListProductSale() {
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
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().editProductOnSaleById
                            (productPrice, productQty, productDiscount, productId);
                    handler.post(() -> {
                        bottomSheetDialog.dismiss();
                        onClearSaleTotal();
                        onResetData();
                        onGetAllSaleProduct();
                    });
                }).start();
            });
            bottomSheetDialog.show();
        });
    }

    private void onResetData() {
        saleSubtotal = 0;
        saleProductDiscount = 0;
        saleProductPrice = 0;
        saleProductQty = 0;
        saleDiscount = 0;
    }

    private void onSetFinalPrice() {
        binding.saleTotal.setText(String.valueOf(saleSubtotal));
        binding.saleDiscount.setText("");
        binding.saleSubtotal.setText(String.valueOf(saleSubtotal));
    }

    private void OnSalePay(View view) {
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().deleteAfterPay();
            handler.post(this::onGetAllSaleProduct);
        }).start();
        onClearSaleTotal();
    }

    private void onClearSaleTotal() {
        binding.saleTotal.setText("");
        binding.saleDiscount.setText("");
        binding.saleSubtotal.setText("");
    }

    private void onCreateBottomDialog() {
        SaleBinding = CustomEditProductOnSaleBinding.inflate(getLayoutInflater());
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(SaleBinding.getRoot());
        SaleBinding.customBtnCancelEditProduct.setOnClickListener(v -> bottomSheetDialog.dismiss());

    }


    private void onGetAllSaleProduct() {
        new Thread(() -> {
            transactionList = POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSaleTransaction();
            handler.post(() -> binding.listItemSale.setAdapter(new AdapterSale(this, requireContext(),
                    transactionList)));
            handler.post(() -> {
                onClickListProductSale();
                onCalculateProductSale();
            });
        }).start();
    }

    @Override
    public void doRefresh() {
        onGetAllSaleProduct();
    }
}