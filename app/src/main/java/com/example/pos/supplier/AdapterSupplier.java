package com.example.pos.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Supplier;
import com.example.pos.databinding.CustomSupplierListBinding;

import java.util.List;
import java.util.Objects;

public class AdapterSupplier extends BaseAdapter {
    CustomSupplierListBinding binding;
    List<Supplier> suppliers;
    Context context;

    public AdapterSupplier(List<Supplier> suppliers, Context context) {
        this.suppliers = suppliers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return suppliers.size();
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
            binding =
                    CustomSupplierListBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
        }
        binding.customSupplierName.setText(suppliers.get(i).getSupplierName());
        return view;
    }

    public int getPosition(int position) {
        int index = -1;
        for (int i = 0; i < suppliers.size(); i++) {
            if (Objects.equals(suppliers.get(i).getSupplierId(), position)) {
                index = i;
            }
        }

        return index;
    }
}
