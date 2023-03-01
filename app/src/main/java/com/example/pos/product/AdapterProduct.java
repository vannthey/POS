package com.example.pos.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.Product;
import com.example.pos.databinding.CustomProductModelBinding;

import java.util.List;

public class AdapterProduct extends BaseAdapter {
    CustomProductModelBinding binding;
    List<Product> productList;
    Context ctx;

    public AdapterProduct(List<Product> productList, Context ctx) {
        this.productList = productList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return productList.size();
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
            binding = CustomProductModelBinding.inflate(LayoutInflater.from(ctx), viewGroup,
                    false);
            view = binding.getRoot();
        }

        binding.customProductName.setText(productList.get(i).getProductName());
        binding.customProductPrice.setText(String.valueOf(productList.get(i).getProductPrice()));
        binding.customProductQty.setText(String.valueOf(productList.get(i).getProductQty()));
        Glide.with(ctx).load(productList.get(i).getImagePath()).into(binding.rowImageProduct);
        if ((productList.get(i).getProductQty() > 0)) {
            binding.customCheckProductQty.setText("x");
        } else if (productList.get(i).getProductQty() == 0) {
            binding.customCheckProductQty.setText("<");
        }


        return view;
    }
}
