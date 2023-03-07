package com.example.pos.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.Customer;
import com.example.pos.databinding.CustomListAllCustomerBinding;

import java.util.List;

public class AdapterCustomer extends BaseAdapter {
    List<Customer> customerList;
    Context ctx;
    CustomListAllCustomerBinding binding;
    Holder holder;

    public AdapterCustomer(List<Customer> customerList, Context ctx) {
        this.customerList = customerList;
        this.ctx = ctx;
    }

    static class Holder {
        View convertView;
        CustomListAllCustomerBinding customerBinding;

        public Holder(CustomListAllCustomerBinding customerBinding) {
            this.customerBinding = customerBinding;
            convertView = customerBinding.getRoot();
        }
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int i) {
        return customerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            binding = CustomListAllCustomerBinding.inflate(LayoutInflater.from(ctx), viewGroup, false);
            holder = new Holder(binding);
            holder.convertView = binding.getRoot();
            holder.convertView.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.customerBinding.customCustomerName.setText(customerList.get(i).getCustomerName());
        holder.customerBinding.customCustomerPhone.setText(customerList.get(i).getCustomerPhoneNumber());
        return holder.convertView;
    }
}
