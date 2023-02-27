package com.example.pos.sale;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.databinding.CustomItemSaleBinding;

import java.util.List;

public class AdapterSale extends BaseAdapter {
    CustomItemSaleBinding binding;
    List<SaleTransaction> transactionList;
    Context context;
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
            binding = CustomItemSaleBinding.inflate(LayoutInflater.from(context),viewGroup,false);
            view = binding.getRoot();
        }

        return view;
    }
}
