package com.example.pos.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.databinding.CustomProductSaleBinding;

import java.util.List;

public class AdapterSale extends BaseAdapter {

    DeleteProductCallBack productCallBack;
    Holder holder;
    CustomProductSaleBinding binding;
    Context context;
    List<SaleTransaction> transactionList;

    public AdapterSale(DeleteProductCallBack productCallBack, Context context, List<SaleTransaction> transactionList) {
        this.productCallBack = productCallBack;
        this.context = context;
        this.transactionList = transactionList;
    }

    static class Holder {
        View vu;
        CustomProductSaleBinding productSaleBinding;

        public Holder(CustomProductSaleBinding productSaleBinding) {
            this.productSaleBinding = productSaleBinding;
            this.vu = productSaleBinding.getRoot();
        }
    }

    @Override
    public int getCount() {
        return transactionList.size();
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
            binding = CustomProductSaleBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            holder = new Holder(binding);
            holder.vu = binding.getRoot();
            holder.vu.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        double discount = (transactionList.get(i).getProductDiscount()) / 100;
        int qty = transactionList.get(i).getProductQty();
        double price = transactionList.get(i).getProductPrice();
        double subtotal = price * qty;
        if (discount == 0) {
            subtotal = price * qty;
            holder.productSaleBinding.customSubtotalItemSale.setText(String.valueOf(subtotal));
        } else {
            subtotal = subtotal - discount;
            holder.productSaleBinding.customSubtotalItemSale.setText(String.valueOf(subtotal));
        }
        holder.productSaleBinding.customDiscountItemSale.setText(String.valueOf(transactionList.get(i).getProductDiscount()));
        holder.productSaleBinding.customNameItemSale.setText(transactionList.get(i).getProductName());
        holder.productSaleBinding.customPriceItemSale.setText(String.valueOf(transactionList.get(i).getProductPrice()));
        holder.productSaleBinding.customQtyItemSale.setText(String.valueOf(transactionList.get(i).productQty));
        holder.productSaleBinding.customDeleteItemSale.setOnClickListener(view1 -> {
            productCallBack.doDelete(transactionList.get(i).saleId);
        });
        return holder.vu;
    }

}
