package com.example.pos.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Supplier;
import com.example.pos.databinding.CustomSupplierSpinnerBinding;

import java.util.List;

public class AdapterSupplierSpinner extends BaseAdapter {
    CustomSupplierSpinnerBinding binding;
    List<Supplier> supplierList;
    Context ctx;

    public AdapterSupplierSpinner(List<Supplier> supplierList, Context ctx) {
        this.supplierList = supplierList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return supplierList.size();
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
            binding = CustomSupplierSpinnerBinding.inflate(LayoutInflater.from(ctx), viewGroup,
                    false);
            view = binding.getRoot();
        }
        binding.customSupplierNameSpinner.setText(supplierList.get(i).getSupplierName());
        return view;
    }
}
