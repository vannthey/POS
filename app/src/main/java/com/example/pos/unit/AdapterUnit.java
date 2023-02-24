package com.example.pos.unit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Unit;
import com.example.pos.databinding.CustomUnitItemBinding;

import java.util.List;

public class AdapterUnit extends BaseAdapter {
    CustomUnitItemBinding binding;
    List<Unit> unitList;
    Context context;

    public AdapterUnit(List<Unit> unitList, Context context) {
        this.unitList = unitList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return unitList.size();
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
            binding = CustomUnitItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
        }
        binding.customUnitName.setText(unitList.get(i).getUnitTitle());
        return view;
    }
}
