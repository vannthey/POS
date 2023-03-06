package com.example.pos.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Inventory;
import com.example.pos.databinding.CustomInventoryItemBinding;

import java.util.List;
import java.util.Objects;

public class AdapterInventory extends BaseAdapter {
    CustomInventoryItemBinding binding;
    List<Inventory> inventoryList;
    Context ctx;

    public AdapterInventory(List<Inventory> inventoryList, Context ctx) {
        this.inventoryList = inventoryList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return inventoryList.size();
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
        binding.inventoryName.setText(inventoryList.get(i).getInventoryName());
        return view;
    }

    public int getPosition(int position) {
        int index = -1;
        for (int i = 0; i < inventoryList.size(); i++) {
            if (Objects.equals(inventoryList.get(i).getInventoryId(), position)) {
                index = i;
            }
        }

        return index;
    }
}
