package com.example.pos.dasboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.pos.R;
import com.example.pos.dasboard.DashboardModel;
import com.example.pos.setting.Login;

import java.util.List;
import java.util.Random;

public class DashboardAdapter extends BaseAdapter {
    List<DashboardModel> model;
    Context context;

    public DashboardAdapter(List<DashboardModel> model, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.custom_dashboard_model_items, viewGroup, false);
        }
        TextView item_name = view.findViewById(R.id.item_name);
        CardView cardView_dashboard = view.findViewById(R.id.cardView_dashboard);
//        Random rnd = new Random();
//         int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        int color= ((int)(Math.random()*16777215)) | (0xFF << 24);
//        cardView_dashboard.setCardBackgroundColor(color);
        item_name.setText(model.get(i).getItem_name());


        return view;
    }
}
