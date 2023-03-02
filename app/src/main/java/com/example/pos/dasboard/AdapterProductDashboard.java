package com.example.pos.dasboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.Product;
import com.example.pos.databinding.CustomDashboardModelItemsBinding;

import java.util.List;

public class AdapterProductDashboard extends BaseAdapter {
    CustomDashboardModelItemsBinding binding;
    List<Product> productList;
    Context ctx;
    Holder holder;

    public AdapterProductDashboard(List<Product> productList, Context ctx) {
        this.productList = productList;
        this.ctx = ctx;
    }

    /*
    Hold view
     */
    static class Holder {
        View convertView;
        CustomDashboardModelItemsBinding modelItemsBinding;

        public Holder(CustomDashboardModelItemsBinding modelItemsBinding) {
            this.modelItemsBinding = modelItemsBinding;
            this.convertView = modelItemsBinding.getRoot();
        }
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            binding = CustomDashboardModelItemsBinding.inflate(LayoutInflater.from(ctx), viewGroup, false);
            holder = new Holder(binding);
            holder.convertView = binding.getRoot();
            holder.convertView.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.modelItemsBinding.productNameDashboard.setText(productList.get(i).getProductName());
        holder.modelItemsBinding.productPriceDashboard.setText(String.valueOf(productList.get(i).getProductPrice()));
        Glide.with(ctx).load(productList.get(i).getImagePath()).into(holder.modelItemsBinding.productImageDashboard);

        return holder.convertView;
    }
}
