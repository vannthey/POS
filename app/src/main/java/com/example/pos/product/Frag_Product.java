package com.example.pos.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.POSDatabase;
import com.example.pos.databinding.FragmentFragProductBinding;

import java.util.List;

public class Frag_Product extends Fragment {
    FragmentFragProductBinding binding;
    List<Product> itemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragProductBinding.inflate(getLayoutInflater(), container, false);
        onShowAllProduct();
        return binding.getRoot();
    }

    private void onShowAllProduct() {

        new Thread(()->{
            itemList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
        }).start();

    }
}