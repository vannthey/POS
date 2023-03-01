package com.example.pos.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.POSDatabase;
import com.example.pos.databinding.CustomProductSaleBinding;

import java.util.List;

public class AdapterSale extends BaseAdapter {

    DeleteProductCallBack productCallBack;

    CustomProductSaleBinding binding;
    Context context;
    List<SaleTransaction> transactionList;

    public AdapterSale(DeleteProductCallBack productCallBack, Context context, List<SaleTransaction> transactionList) {
        this.productCallBack = productCallBack;
        this.context = context;
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Frag_sale frag_sale = new Frag_sale();
        if (view == null) {
            binding = CustomProductSaleBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
        }
        long numRow = getItemId(i) + 1;
        double discount = (transactionList.get(i).getProductDiscount()) / 100;
        int qty = transactionList.get(i).getProductQty();
        double price = transactionList.get(i).getProductPrice();
        double subtotal = price * qty;
        if (discount == 0) {
            subtotal = price * qty;
            binding.customSubtotalItemSale.setText(String.valueOf(subtotal));
        } else {
            subtotal = subtotal - discount;
            binding.customSubtotalItemSale.setText(String.valueOf(subtotal));
        }

        Glide.with(context).load(transactionList.get(i).getProductImagePath()).into(binding
                .customImageItemSale);
        binding.customDiscountItemSale.setText(String.valueOf(transactionList.get(i).getProductDiscount()));
        binding.customNameItemSale.setText(transactionList.get(i).getProductName());
        binding.customPriceItemSale.setText(String.valueOf(transactionList.get(i).getProductPrice()));
        binding.customQtyItemSale.setText(String.valueOf(transactionList.get(i).productQty));
        binding.customNumRowItemSale.setText(String.valueOf(numRow));
        binding.customDeleteItemSale.setOnClickListener(view1 -> {
            new Thread(() -> {
                POSDatabase.getInstance(context.getApplicationContext()).getDao().deleteSaleTransactionById(transactionList.get(i).getSaleId());
                productCallBack.doRefresh();
            }).start();

        });
        return view;
    }

}
