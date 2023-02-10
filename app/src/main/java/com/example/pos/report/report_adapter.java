package com.example.pos.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.R;

import java.util.List;

public class report_adapter extends BaseAdapter {
    List<String> report_item;
    Context context;

    public report_adapter(List<String> report_item, Context context) {
        this.report_item = report_item;
        this.context = context;
    }

    @Override
    public int getCount() {
        return report_item.size();
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
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.custom_report_model_items,viewGroup,false);
        }
        return view;
    }
}
