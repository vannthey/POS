package com.example.pos.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Inventory;
import com.example.pos.databinding.CustomInventoryItemBinding;

import java.util.List;

public class AdapterInventory extends BaseAdapter {
    CustomInventoryItemBinding binding;
    List<Inventory> warehouseList;
    Context ctx;

    public AdapterInventory(List<Inventory> warehouseList, Context ctx) {
        this.warehouseList = warehouseList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return warehouseList.size();
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
            binding = CustomInventoryItemBinding.inflate(LayoutInflater.from(ctx), viewGroup, false);
            view = binding.getRoot();
        }
        binding.inventoryName.setText(warehouseList.get(i).getInventoryName());
        return view;
    }
}
