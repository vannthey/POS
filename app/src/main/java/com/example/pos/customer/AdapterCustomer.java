package com.example.pos.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.Customer;
import com.example.pos.MainActivity;
import com.example.pos.R;
import com.example.pos.databinding.CustomListAllCustomerBinding;

import java.util.List;

public class AdapterCustomer extends BaseAdapter {
    private final String TAG = "customerId";
    CustomerHelper customer;
    List<Customer> customerList;
    Context ctx;
    CustomListAllCustomerBinding binding;
    Holder holder;
    int isVisible;
    MainActivity mainActivity;

    public AdapterCustomer(CustomerHelper customer, List<Customer> customerList, Context ctx) {
        this.customer = customer;
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

        //isVisible == 0 set visible to GONE and 1 set visible VISIBLE
        if (isVisible == 0) {
            holder.customerBinding.customCustomerProfile.setVisibility(View.GONE);
            holder.customerBinding.customCustomerPhone.setVisibility(View.GONE);
            holder.customerBinding.customCustomerAddress.setVisibility(View.GONE);
            holder.customerBinding.customCustomerPreference.setVisibility(View.GONE);
            holder.customerBinding.customCustomerCard.setCardElevation(0);
        } else if (isVisible == 1) {
            if (customerList.get(i).getCustomerProfile() != null) {
                Glide.with(ctx).load(customerList.get(i).getCustomerProfile()).into(holder.customerBinding.customCustomerProfile);
            } else {
                holder.customerBinding.customCustomerProfile.setImageResource(R.drawable.admin_profile);
            }
            holder.customerBinding.customCustomerPhone.setText(customerList.get(i).getCustomerPhoneNumber());
            holder.customerBinding.customCustomerAddress.setText(customerList.get(i).getCustomerAddress());
            holder.customerBinding.customCustomerPreference.setOnClickListener(view1 -> customer.doCustomizeCustomerById(customerList.get(i).getCustomerId()));
        }
        holder.customerBinding.customCustomerName.setText(customerList.get(i).getCustomerName());

        return holder.convertView;
    }

    public int isVisible(int visible) {
        isVisible = visible;
        return isVisible;
    }
}
