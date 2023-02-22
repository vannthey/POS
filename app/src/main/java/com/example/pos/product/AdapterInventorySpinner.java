package com.example.pos.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Inventory;
import com.example.pos.databinding.CustomSpinnerInventoryBinding;

import java.util.List;

public class AdapterInventorySpinner extends BaseAdapter {
    CustomSpinnerInventoryBinding binding;
    List<Inventory> inventories;
    Context ctx;

    @Override
    public int getCount() {
        return inventories.size();
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
            binding = CustomSpinnerInventoryBinding.inflate(LayoutInflater.from(ctx), viewGroup, false);
            view = binding.getRoot();
        }

        binding.spinnerListInventory.setText(inventories.get(i).getInventoryName());

        return view;
    }
}
