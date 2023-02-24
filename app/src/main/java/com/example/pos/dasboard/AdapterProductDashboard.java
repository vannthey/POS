package com.example.pos.dasboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Product;
import com.example.pos.databinding.CustomDashboardModelItemsBinding;

import java.util.List;
import java.util.Random;

public class AdapterProductDashboard extends BaseAdapter {
    CustomDashboardModelItemsBinding binding;
    List<Product> itemList;
    Context ctx;

    public AdapterProductDashboard(List<Product> itemList, Context ctx) {
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
            binding = CustomDashboardModelItemsBinding.inflate(LayoutInflater.from(ctx), viewGroup,
                    false);
            view = binding.getRoot();
        }
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        binding.itemName.setText(itemList.get(i).getProductName());
        binding.dashboardItemPrice.setText(String.valueOf(itemList.get(i).getProductPrice()));
        binding.headerItemView.setBackgroundColor(color);

        return view;
    }
}
