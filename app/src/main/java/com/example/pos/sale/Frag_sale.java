package com.example.pos.sale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.POSDatabase;
import com.example.pos.databinding.FragmentFragSaleBinding;

import java.util.List;


public class Frag_sale extends Fragment {
    FragmentFragSaleBinding binding;
    List<SaleTransaction> transactionList;
    int productId;
    int productQty;
    double productPrice;
    double productDiscount;
    Handler handler;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentFragSaleBinding.inflate(inflater, container, false);
        handler = new Handler();
       onGetAllSaleProduct();
       onClickListProductSale();
       return binding.getRoot();
    }

    private void onClickListProductSale() {
        binding.listItemSale.setOnItemClickListener((adapterView, view, i, l) -> {
            for (int j = 0; j < transactionList.size(); j++) {

            }
        });
    }

    private void onGetAllSaleProduct() {
        new Thread(()->{
            transactionList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSaleTransaction();
            handler.post(()->{
                binding.listItemSale.setAdapter(new AdapterSale(requireContext(),transactionList));
            });
        }).start();
    }
}