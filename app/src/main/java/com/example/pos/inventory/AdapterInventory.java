package com.example.pos.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pos.Database.Entity.Warehouse;
import com.example.pos.R;

import java.util.List;

public class AdapterInventory extends BaseAdapter {
    List<Warehouse> warehouseList;
    Context ctx;

    public AdapterInventory(List<Warehouse> warehouseList, Context ctx) {
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
        if (view==null){
            view = LayoutInflater.from(ctx).inflate(R.layout.custom_inventory_item,viewGroup,false);
        }
        TextView inventory_name = view.findViewById(R.id.inventory_name);
        inventory_name.setText(warehouseList.get(i).getWarehouseName());
        return view;
    }
}
