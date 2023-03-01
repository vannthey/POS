package com.example.pos.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.POSDatabase;
import com.example.pos.databinding.CustomItemSaleBinding;

import java.util.List;

public class AdapterSale extends BaseAdapter {
    CustomItemSaleBinding binding;
    Context context;
    List<SaleTransaction> transactionList;

    public AdapterSale(Context context, List<SaleTransaction> transactionList) {
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

        if (view == null) {
            binding = CustomItemSaleBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
        }
        long numRow = getItemId(i);
        Glide.with(context).load(transactionList.get(i).getProductImagePath()).into(binding.customImageItemSale);
        binding.customDiscountItemSale.setText("0");
        binding.customSubtotalItemSale.setText("0");
        binding.customNameItemSale.setText(transactionList.get(i).getProductName());
        binding.customPriceItemSale.setText(String.valueOf(transactionList.get(i).getProductPrice()));
        binding.customQtyItemSale.setText(String.valueOf(transactionList.get(i).productQty));
        binding.customNumRowItemSale.setText(String.valueOf(numRow += 1));
        binding.customDeleteItemSale.setOnClickListener(view1 -> {
            new Thread(() -> {
                POSDatabase.getInstance(context.getApplicationContext()).getDao().deleteSaleTransactionById(transactionList.get(i).getSaleId());
            }).start();
        });

        return view;
    }
}
