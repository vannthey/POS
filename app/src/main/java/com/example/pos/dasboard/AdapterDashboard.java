package com.example.pos.dasboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.Product;
import com.example.pos.databinding.CustomDashboardModelItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterDashboard extends BaseAdapter implements Filterable {
    CustomDashboardModelItemsBinding binding;
    List<Product> productList;
    List<Product> newProductList;
    Context ctx;
    Holder holder;

    public AdapterDashboard(List<Product> productList, Context ctx) {
        this.productList = productList;
        this.ctx = ctx;
        this.newProductList = new ArrayList<>(productList);
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

    @Override
    public Filter getFilter() {
        return searchItem;
    }

    private final Filter searchItem = new Filter() {
        String searchPattern;

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Product> productListTextSearch = new ArrayList<>();
            //check if user not input any thing so get all value from new list that declare in scope
            //equal to list before it filter so it mean set old data back
            Frag_Dashboard frag_dashboard = new Frag_Dashboard();
            if (charSequence == null || charSequence.length() == 0) {
                productListTextSearch.addAll(newProductList);
            } else {
                searchPattern = charSequence.toString().toLowerCase().trim();
                for (Product product : newProductList) {
                    if (product.getProductName().toLowerCase().contains(searchPattern)) {
                        productListTextSearch.add(product);
                    } else if (String.valueOf(product.getProductCode()).contains(searchPattern)) {
                        productListTextSearch.add(product);
                    } else if (String.valueOf(product.getCategoryName()).contains(searchPattern)) {
                        productListTextSearch.add(product);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = productListTextSearch;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productList.clear();
            productList.addAll((List<Product>) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
