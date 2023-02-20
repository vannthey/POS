package com.example.pos.category;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.pos.R;

import java.util.List;
import java.util.Random;

public class CategoryAdapter extends BaseAdapter {
    List<CategoryModel> model;
    Context context;

    public CategoryAdapter(List<CategoryModel> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @Override
    public int getCount() {
        return model.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.custom_category_model_items, viewGroup, false);
        }
        TextView item_category = view.findViewById(R.id.item_category);
        TextView no_category_item = view.findViewById(R.id.no_category_item);
        CardView cardView_category = view.findViewById(R.id.cardView_category);

//        Random rnd = new Random();
//        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        cardView_category.setCardBackgroundColor(color);
        item_category.setText(model.get(i).getIccategory());

        return view;
    }
}
