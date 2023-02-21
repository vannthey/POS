package com.example.pos.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
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
        binding.btnSaveProduct.setOnClickListener(this::onSaveProduct);
        binding.btnCancelAddProduct.setOnClickListener(this::onCancelSaveProduct);
        onShowAllProduct();
        return binding.getRoot();
    }

    private void onCancelSaveProduct(View view) {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
    }

    private void onSaveProduct(View view) {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
        onShowAllProduct();
    }

    private void onShowAllProduct() {

        new Thread(() -> {
            itemList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_product) {
            binding.layoutAddProduct.setVisibility(View.VISIBLE);
            binding.layoutShowProduct.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_product).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}