package com.example.pos.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Category;
import com.example.pos.databinding.CustomCategoryModelItemsBinding;

import java.util.List;
import java.util.Objects;

public class AdapterCategory extends BaseAdapter {
    CustomCategoryModelItemsBinding binding;
    List<Category> categories;

    Context context;

    public AdapterCategory(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            binding = CustomCategoryModelItemsBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
        }
        binding.itemCategory.setText(categories.get(i).getCategoryName());

        return view;
    }

    public int getPosition(int position) {
        int index = -1;
        for (int i = 0; i < categories.size(); i++) {
            if (Objects.equals(categories.get(i).getCategoryId(), position)) {
                index = i;
            }
        }

        return index;
    }
}
