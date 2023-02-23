package com.example.pos.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Product;
import com.example.pos.databinding.CustomProductItemBinding;

import java.util.List;

public class AdapterProduct extends BaseAdapter {
    CustomProductItemBinding binding;
    List<Product> itemList;
    Context ctx;

    public AdapterProduct(List<Product> itemList, Context ctx) {
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return itemList.size();
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
            binding = CustomProductItemBinding.inflate(LayoutInflater.from(ctx), viewGroup,
                    false);
            view = binding.getRoot();
        }

        binding.customProductName.setText(itemList.get(i).getProductName());
        binding.customProductPrice.setText(String.valueOf(itemList.get(i).getProductPrice()));
        binding.customProductQty.setText(String.valueOf(itemList.get(i).getProductQty()));
        long numRow = getItemId(i)+1;

            binding.numRowProduct.setText(String.valueOf(numRow));

        return view;
    }
}
